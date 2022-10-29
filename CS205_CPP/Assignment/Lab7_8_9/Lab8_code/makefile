CC = g++ 
TARGET = Lab8
OBJ = candybar.o Lab8.o

CFLAGES = -std=c++17 -stdlib=libc++ -c -Wall

$(TARGET) : $(OBJ)
	$(CC) -o $@ $(OBJ)

%.o : %.cpp
	$(CC) $(CFLAGES) $< -o $@

.PHONY: clean
clean:
	rm -f *.o $(TARGET)