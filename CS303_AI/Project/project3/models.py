import torch
from torch import nn


class PointClassifier(nn.Module):
    def __init__(self, input, hidden1, hidden2, output):
        super(PointClassifier, self).__init__()
        
        self.linear = nn.Sequential(
            nn.Linear(input, hidden1),
            nn.ReLU(inplace=True),
            nn.Linear(hidden1, hidden2),
            nn.ReLU(inplace=True),
            nn.Linear(hidden2, output),
            nn.Softmax(dim=1)
        )

    def forward(self, X):
        '''
        Args:
            X: shape (N,256)
        Return: Tensor in shape (N, 1)
            Represent the class of the feature
        '''
        return self.linear(X)




def predict(classifier, x):
    classifier.eval()
    y_hat = classifier.forward(x)
    y_hat = torch.argmax(y_hat, dim=1) 
    return y_hat



    