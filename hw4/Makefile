CC = javac
CP = "lib/:bin/"
OUT = bin/
RN = java
ROUND = 100
CHIPS = 100
PLAYER1 = PlayerB02902031
PLAYER2 = PlayerB02902031
PLAYER3 = PlayerB03902084
PLAYER4 = PlayerB03902084
MAIN = POOCasino
HPLAYER = PlayerHuman
all:
	mkdir -p bin/
	$(CC) -cp $(CP) src/PlayerB02902031.java -d lib
	$(CC) -cp $(CP) src/Arranger.java src/Shuffler.java src/POOCasino.java src/Checker.java src/Dealer.java -d $(OUT)
run:
	$(RN) -cp $(CP) $(MAIN) $(ROUND) $(CHIPS) $(PLAYER1) $(PLAYER2) $(PLAYER3) $(PLAYER4)
Human:
	$(CC) -cp $(CP) src/PlayerHuman.java -d lib
runHuman:
	$(RN) -cp $(CP) $(MAIN) $(ROUND) $(CHIPS) $(PLAYER1) $(PLAYER1) $(PLAYER2) $(HPLAYER)
bonus:
	$(CC) -cp $(CP) src/bonus/*.java -d $(OUT)
	$(CC) -cp $(CP) src/*.java -d $(OUT)
make bonusRun:
	$(RN) -cp $(CP) BonusPOOCasino 100 10000
make client:
	$(RN) -cp $(CP) Client 127.0.0.1 10000
clean:
	rm -rf bin/
