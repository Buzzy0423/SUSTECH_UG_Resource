# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.20

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:

#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:

# Disable VCS-based implicit rules.
% : %,v

# Disable VCS-based implicit rules.
% : RCS/%

# Disable VCS-based implicit rules.
% : RCS/%,v

# Disable VCS-based implicit rules.
% : SCCS/s.%

# Disable VCS-based implicit rules.
% : s.%

.SUFFIXES: .hpux_make_needs_suffix_list

# Command-line flag to silence nested $(MAKE).
$(VERBOSE)MAKESILENT = -s

#Suppress display of executed commands.
$(VERBOSE).SILENT:

# A target that is always out of date.
cmake_force:
.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake -E rm -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/lee/课件/CS205_C++/Assignment/AS2

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/AS2.dir/depend.make
# Include the progress variables for this target.
include CMakeFiles/AS2.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/AS2.dir/flags.make

CMakeFiles/AS2.dir/code/assign2.cpp.o: CMakeFiles/AS2.dir/flags.make
CMakeFiles/AS2.dir/code/assign2.cpp.o: ../code/assign2.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/AS2.dir/code/assign2.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/AS2.dir/code/assign2.cpp.o -c /Users/lee/课件/CS205_C++/Assignment/AS2/code/assign2.cpp

CMakeFiles/AS2.dir/code/assign2.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/AS2.dir/code/assign2.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/lee/课件/CS205_C++/Assignment/AS2/code/assign2.cpp > CMakeFiles/AS2.dir/code/assign2.cpp.i

CMakeFiles/AS2.dir/code/assign2.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/AS2.dir/code/assign2.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/lee/课件/CS205_C++/Assignment/AS2/code/assign2.cpp -o CMakeFiles/AS2.dir/code/assign2.cpp.s

CMakeFiles/AS2.dir/code/test.cpp.o: CMakeFiles/AS2.dir/flags.make
CMakeFiles/AS2.dir/code/test.cpp.o: ../code/test.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building CXX object CMakeFiles/AS2.dir/code/test.cpp.o"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/AS2.dir/code/test.cpp.o -c /Users/lee/课件/CS205_C++/Assignment/AS2/code/test.cpp

CMakeFiles/AS2.dir/code/test.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/AS2.dir/code/test.cpp.i"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/lee/课件/CS205_C++/Assignment/AS2/code/test.cpp > CMakeFiles/AS2.dir/code/test.cpp.i

CMakeFiles/AS2.dir/code/test.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/AS2.dir/code/test.cpp.s"
	/Library/Developer/CommandLineTools/usr/bin/c++ $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/lee/课件/CS205_C++/Assignment/AS2/code/test.cpp -o CMakeFiles/AS2.dir/code/test.cpp.s

# Object files for target AS2
AS2_OBJECTS = \
"CMakeFiles/AS2.dir/code/assign2.cpp.o" \
"CMakeFiles/AS2.dir/code/test.cpp.o"

# External object files for target AS2
AS2_EXTERNAL_OBJECTS =

AS2: CMakeFiles/AS2.dir/code/assign2.cpp.o
AS2: CMakeFiles/AS2.dir/code/test.cpp.o
AS2: CMakeFiles/AS2.dir/build.make
AS2: CMakeFiles/AS2.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking CXX executable AS2"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/AS2.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/AS2.dir/build: AS2
.PHONY : CMakeFiles/AS2.dir/build

CMakeFiles/AS2.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/AS2.dir/cmake_clean.cmake
.PHONY : CMakeFiles/AS2.dir/clean

CMakeFiles/AS2.dir/depend:
	cd /Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/lee/课件/CS205_C++/Assignment/AS2 /Users/lee/课件/CS205_C++/Assignment/AS2 /Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug /Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug /Users/lee/课件/CS205_C++/Assignment/AS2/cmake-build-debug/CMakeFiles/AS2.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/AS2.dir/depend

