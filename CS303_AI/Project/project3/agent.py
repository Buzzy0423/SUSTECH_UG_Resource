import torch
import torch.optim as optim
from typing import Tuple
from time import time
from .models import *
import os

FILE_PATH = os.path.join(os.path.dirname(__file__), 'classifier.pth')

P = 3  # spline degree
N_CTPS = 5  # number of control points

RADIUS = 0.3
N_CLASSES = 10
FEATURE_DIM = 256


def evaluate_modified(
    traj: torch.Tensor,
    target_pos: torch.Tensor,
    target_scores: torch.Tensor,
    radius: float,
):
    cdist = torch.cdist(target_pos, traj)
    d = cdist.min(-1).values
    hit = (d < radius)
    d[hit] = 1
    d[~hit] = radius / d[~hit]
    value = torch.sum(d * target_scores, dim=-1)
    return value


def compute_traj(ctps_inter: torch.Tensor):
    """Compute the discretized trajectory given the second to the second control points"""
    t = torch.linspace(0, N_CTPS-P, 100, device=ctps_inter.device)
    knots = torch.cat([
        torch.zeros(P, device=ctps_inter.device),
        torch.arange(N_CTPS+1-P, device=ctps_inter.device),
        torch.full((P,), N_CTPS-P, device=ctps_inter.device),
    ])
    ctps = torch.cat([
        torch.tensor([[0., 0.]], device=ctps_inter.device),
        ctps_inter,
        torch.tensor([[N_CTPS, 0.]], device=ctps_inter.device)
    ])
    return splev(t, knots, ctps, P)


def evaluate(
    traj: torch.Tensor,
    target_pos: torch.Tensor,
    target_scores: torch.Tensor,
    radius: float,
) -> torch.Tensor:
    """Evaluate the trajectory and return the score it gets.

    Parameters
    ----------
    traj: Tensor of shape `(*, T, 2)`
        The discretized trajectory, where `*` is some batch dimension and `T` is the discretized time dimension.
    target_pos: Tensor of shape `(N, 2)`
        x-y positions of shape where `N` is the number of targets. 
    target_scores: Tensor of shape `(N,)`
        Scores you get when the corresponding targets get hit. 
    """
    cdist = torch.cdist(
        target_pos, traj)  # see https://pytorch.org/docs/stable/generated/torch.cdist.html
    d = cdist.min(-1).values
    hit = (d < radius)
    value = torch.sum(hit * target_scores, dim=-1)
    return value


def splev(
    x: torch.Tensor,
    knots: torch.Tensor,
    ctps: torch.Tensor,
    degree: int,
    der: int = 0
) -> torch.Tensor:
    """Evaluate a B-spline or its derivatives.

    See https://en.wikipedia.org/wiki/B-spline for more about B-Splines.
    This is a PyTorch implementation of https://en.wikipedia.org/wiki/De_Boor%27s_algorithm

    Parameters
    ----------
    x : Tensor of shape `(t,)`
        An array of points at which to return the value of the smoothed
        spline or its derivatives.
    knots: Tensor of shape `(m,)`
        A B-Spline is a piece-wise polynomial. 
        The values of x where the pieces of polynomial meet are known as knots.
    ctps: Tensor of shape `(n_ctps, dim)`
        Control points of the spline.
    degree: int
        Degree of the spline.
    der: int, optional
        The order of derivative of the spline to compute (must be less than
        or equal to k, the degree of the spline).
    """
    if der == 0:
        return _splev_torch_impl(x, knots, ctps, degree)
    else:
        assert der <= degree, "The order of derivative to compute must be less than or equal to k."
        n = ctps.size(-2)
        ctps = (ctps[..., 1:, :]-ctps[..., :-1, :]) / \
            (knots[degree+1:degree+n]-knots[1:n]).unsqueeze(-1)
        return degree * splev(x, knots[..., 1:-1], ctps, degree-1, der-1)


def _splev_torch_impl(x: torch.Tensor, t: torch.Tensor, c: torch.Tensor, k: int):
    """
        x: (t,)
        t: (m, ) 
        c: (n_ctps, dim)
    """
    assert t.size(0) == c.size(0) + k + \
        1, f"{len(t)} != {len(c)} + {k} + {1}"  # m= n + k + 1

    x = torch.atleast_1d(x)
    assert x.dim() == 1 and t.dim() == 1 and c.dim(
    ) == 2, f"{x.shape}, {t.shape}, {c.shape}"
    n = c.size(0)
    u = (torch.searchsorted(t, x)-1).clip(k, n-1).unsqueeze(-1)
    x = x.unsqueeze(-1)
    d = c[u-k+torch.arange(k+1, device=c.device)].contiguous()
    for r in range(1, k+1):
        j = torch.arange(r-1, k, device=c.device) + 1
        t0 = t[j+u-k]
        t1 = t[j+u+1-r]
        alpha = ((x - t0) / (t1 - t0)).unsqueeze(-1)
        d[:, j] = (1-alpha)*d[:, j-1] + alpha*d[:, j]
    return d[:, k]



class Agent:
    def __init__(self) -> None:
        """Initialize the agent, e.g., load the classifier model. """
        self.classifier = PointClassifier(256, 128, 64, 10)
        self.classifier.load_state_dict(torch.load(FILE_PATH))
        self.time_limit = 0.3

    def random_pos(self, target_pos, target_features, class_scores):
        start_time = time()
        cls = predict(self.classifier, target_features)
        target_score = class_scores[cls]
        best_score = torch.tensor(-100000)
        best_action = torch.Tensor([[1, 0], [2, 0], [3, 0]])
        iter = 0
        while time()-start_time < 0.27:
            iter += 1
            ctps_inter = torch.rand(
                (N_CTPS-2, 2)) * torch.tensor([N_CTPS-2, 2.]) + torch.tensor([1., -1.])
            traj = compute_traj(ctps_inter)
            score = evaluate(traj, target_pos, target_score, RADIUS)
            if score > best_score:
                best_score = score
                best_action = ctps_inter
        return best_action

    def get_init(self, target_pos, target_score):
        best_score = -1000
        best_action = None
        iter = 0
        while iter < 200:
            tmp = torch.rand(
                (N_CTPS-2, 2)) * torch.tensor([N_CTPS-2, 2.]) + torch.tensor([1., -1.])
            score = evaluate(compute_traj(tmp), target_pos,
                             target_score, RADIUS)
            iter += 1
            if score > best_score:
                best_score = score.item()
                best_action = torch.clone(tmp)
        return best_action, best_score

    def gd(self, target_pos, target_features, class_scores):
        start_time = time()
        cls = predict(self.classifier, target_features)
        target_score = class_scores[cls]
        lr = 0.01
        best_score = torch.tensor(-10000)
        tmp_action = torch.Tensor([[1, 0], [2, 0], [3, 0]])

        while time() - start_time < 0.1:
            tmp = torch.rand(
                (N_CTPS-2, 2)) * torch.tensor([N_CTPS-2, 2.]) + torch.tensor([1., -1.])
            score = evaluate(compute_traj(tmp), target_pos,
                             target_score, RADIUS)
            if score > best_score:
                best_score = score.item()
                tmp_action = torch.clone(tmp)

        best_action = torch.clone(tmp_action.detach())
        tmp_action.requires_grad_(True)
        optimizer = optim.Adam([tmp_action], lr=lr)

        while time() - start_time < 0.28:
            optimizer.zero_grad()
            traj = compute_traj(tmp_action)
            score = -evaluate_modified(traj, target_pos, target_score, RADIUS)
            real_score = evaluate(traj, target_pos, target_score, RADIUS)
            score.backward()
            optimizer.step()
            if real_score > best_score:
                best_score = real_score.detach().item()
                best_action = torch.clone(tmp_action.detach())

        if best_score < 0:
            return torch.Tensor([[10000, 10000], [10000, 10000], [10000, 10000]])
        else:
            return best_action

    def get_action(self,
                   target_pos: torch.Tensor,
                   target_features: torch.Tensor,
                   class_scores: torch.Tensor,
                   ) -> Tuple[torch.Tensor, torch.Tensor]:
        """Compute the parameters required to fire a projectile. 

        Args:
            target_pos: x-y positions of shape `(N, 2)` where `N` is the number of targets. 
            target_features: features of shape `(N, d)`.
            class_scores: scores associated with each class of targets. `(K,)` where `K` is the number of classes.
        Return: Tensor of shape `(N_CTPS-2, 2)`
            the second to the second last control points
        """

        assert len(target_pos) == len(target_features)
        return self.gd(target_pos, target_features, class_scores)
