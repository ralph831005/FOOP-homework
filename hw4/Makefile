CC = javac
CP = "lib/:bin/"
OUT = bin/
RN = java
ROUND = 100
CHIPS = 100
PLAYER1 = PlayerB02902031
PLAYER2 = PlayerB02902031
MAIN = POOCasino
RM = rm
RM_F = -rf
all:
	mkdir -p bin/
	$(CC) -cp $(CP) src/PlayerB02902031.java -d lib
	$(CC) -cp $(CP) src/Arranger.java src/Shuffler.java src/POOCasino.java src/Checker.java src/Dealer.java -d $(OUT)
run:
	$(RN) -cp $(CP) $(MAIN) $(ROUND) $(CHIPS) $(PLAYER1) $(PLAYER1) $(PLAYER2) $(PLAYER2)
clean:
	$(RM) $(RM_F) $(OUT)
	$(RM) -f lib/PlayerB02902031.class
