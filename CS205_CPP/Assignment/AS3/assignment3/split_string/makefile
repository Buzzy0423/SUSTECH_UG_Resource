CC = clang++
LIB = $(LIB_DIR)/pair.so
TARGET = main
CFLAGES = -std=c++17 -stdlib=libc++ -c -Wall
OBJ = main.cpp 
LIB_DIR = ./lib


$(TARGET) : $(OBJ) $(LIB)
	$(CC) -o $@ -L. $(OBJ) $(LIB)

$(LIB_DIR)/pair.so : pair.cpp
	mkdir -p $(LIB_DIR)
	$(CC) -shared -fPIC -o $(LIB) pair.cpp 

clean:
	rm -f *.o *.so $(TARGET) $(LIB)
	rmdir $(LIB_DIR)