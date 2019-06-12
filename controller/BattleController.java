package controller;

import model.*;
import model.collection.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class BattleController {

    public static void selectCardById(String[] commands, Scanner scanner) throws Exception{
        if(commands.length == 2 && commands[0].compareToIgnoreCase("select") == 0
                && commands[1].matches("[\\d]+")) {
            int id = Integer.parseInt(commands[1]);
            if (Shop.checkValidId(id)) {
                String cardName = returnNameById(id);
                Card card = Card.returnCardByIDFromMap(id);
                if (cardIsInGame(cardName)) {
                    if (thisCardIsYours(cardName)) {
                        doActionOnCard(card, scanner);
                    } else {
                        System.out.println("This card does not belong to you");
                    }
                }
            } else {
                System.out.println("Invalid card id");
            }
            AllDatas.hasEnteredBattle = true;
        }
    }

    public static void doActionOnCard(Card card,Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        String[] commands = command.split(" ");
        moveToXY(commands,card);
        attackCommand(commands,card);
        attackCombo(commands,card);
        useHeroSpecialPowerXY(commands,card);
        comboAttackCommand(commands, card);
        if(!AllDatas.didAction){
            System.out.println("Command was not supported");
        }
    }

    public static void comboAttackCommand(String[] commands, Card card){
        ArrayList<Card> comboCards = new ArrayList<>();
        if (commands[0].equals("attack") && commands[1].equals("combo")){
            int opponentID = Integer.parseInt(commands[2]);
            Card opponentCard = Card.returnCardByIDFromMap(opponentID);
            for (int i = 3; i < commands.length; i++) {
                int cardID = Integer.parseInt(commands[i]);
                comboCards.add(Card.returnCardByIDFromMap(cardID));
            }
            comboAttack(card, comboCards, opponentCard);
        }

    }

    public static void comboAttack(Card mainMinion, ArrayList<Card> comboCards, Card opponentCard){

    }

    public static void moveToXY(String[] commands, Card card){
        if(commands.length == 3 && commands[0].compareToIgnoreCase("move") == 0
                && commands[1].compareToIgnoreCase("to") == 0
                && commands[2].matches("\\([\\d]+,[\\d]+\\)")) {
            int x = Integer.parseInt(commands[2].substring(commands[2].indexOf('('), commands[2].indexOf(",")));
            int y = Integer.parseInt(commands[2].substring(commands[2].indexOf(','), commands[2].indexOf(")")));
            checkAllConditionsToMoveCard(card, x, y);
            AllDatas.didAction = true;
        }
    }
    public static void attackCommand(String[] commands, Card card) throws Exception {
        if(commands.length == 2 && commands[0].compareToIgnoreCase("attack") == 0
                && commands[1].matches("[\\d]+")) {
            int id = Integer.parseInt(commands[1]);
            Card opponentIDCard = Card.returnCardByIDFromMap(id);
            checkAllConditionsToAttack(card, opponentIDCard);
            AllDatas.didAction = true;
        }
    }
    public static void attackCombo(String[] commands, Card card){
        if(commands.length>=4 && commands[0].compareToIgnoreCase("attack") == 0
                && commands[1].compareToIgnoreCase("combo") == 0){
            boolean commandIsCorrect = true;
            for(int i=2; i<commands.length; i++){
                if(!commands[i].matches("[\\d]+"))
                    commandIsCorrect = false;
            }
            if(commandIsCorrect){
                //apply combo attack
            }
            AllDatas.didAction = true;
        }
    }
    public static void useHeroSpecialPowerXY(String[] commands, Card card) throws IOException, ParseException {
        if (commands.length == 4 && commands[0].compareToIgnoreCase("use") == 0 &&
                commands[1].compareToIgnoreCase("special") == 0 && commands[2].compareToIgnoreCase("power") == 0 &&
                commands[3].matches("\\([\\d]+,[\\d]+\\)")) {
            int x = Integer.parseInt(commands[3].substring(commands[3].indexOf('('), commands[3].indexOf(",")));
            int y = Integer.parseInt(commands[3].substring(commands[3].indexOf(','), commands[3].indexOf(")")));
            checkConditionsToApplyHeroSpecialPower(card, x, y);
            AllDatas.didAction = true;
        }
    }


    public static String returnNameById(int cardId)throws Exception{
        int idType = cardId/100;
        int id = cardId%100;
        String name = "";
        switch (idType){
            case 1:
                name = Hero.findHeroByID(id).getName();
                break;
            case 2:
                name = Item.findItemByID(id).getName();
                break;
            case 3:
                name = Minion.findMinionByID(id).getName();
                break;
            case 4:
                name = Spell.findSpellByID(id).getName();
                break;
        }
        return name;
    }
    public static boolean thisCardIsYours(String cardName){
        for (Card card:
                Game.getInstance().getPlayer1CardsInField()) {
            if(card.getName().equals(cardName))
                return true;
        }
        if(Game.getInstance().getHeroOfPlayer1().getName().equals(cardName)) return true;
        return false;
    }
    public static boolean thisCardIsEnemy(String cardName){
        for (Card card:
                Game.getInstance().getPlayer2CardsInField()) {
            if(card.getName().equals(cardName))
                return true;
        }
        if(Game.getInstance().getHeroOfPlayer2().getName().equals(cardName)) return true;
        return false;
    }
    public static boolean cardIsInGame(String checkCard){
        for (Card card:
                Game.getInstance().getPlayer1CardsInField()) {
            if(card.getName().equals(checkCard))
                return true;
        }
        for(Card card : Game.getInstance().getPlayer2CardsInField()){
            if(card.getName().equals(checkCard))
                return true;
        }
        if(Game.getInstance().getHeroOfPlayer1().getName().equals(checkCard)) return true;
        if(Game.getInstance().getHeroOfPlayer2().getName().equals(checkCard)) return true;
        return false;
    }

    public static void checkConditionsToApplyHeroSpecialPower(Card heroCard, int x, int y) throws IOException, ParseException {
 //       if(Hero.thisCardIsHero(cardName)){
            if(!((Hero)heroCard).getSpecialPower().matches("Does not have special power")){
                if(Game.getInstance().getPlayer1().getNumOfMana() >= heroCard.getMana()){
                    //apply special power
                }
                else{
                    System.out.println("Your mana is not enough to use hero's special power");
                }
            }
            else{
                System.out.println("This hero does not have special power");
            }
//        }
//        else{
//            Minion minion = (Minion) Minion.getMinionByName(cardName);
//            if(!minion.getSpecialPower().matches("Does not have special power")){
//                if(Game.getInstance().getPlayer1().getNumOfMana() >= minion.getMana()) {
//                    //apply special power
//                }
//                else {
//                    System.out.println("Your mana is not enough to use minion's special power");
//                }
//            }
//            else{
//                System.out.println("This minion does not have special power");
//            }
//        }
    }

    public static void checkAllConditionsToMoveCard(Card card, int x, int y){
        move(card, x, y);
//        if(Game.getInstance().getHeroOfPlayer1().getName().equals(cardName)){
//            move(Game.getInstance().getHeroOfPlayer1(), x, y);
//        }
    }

    public static void startGameCommand(String[] commands){
        if (commands[0].equals("start") && commands[1].equals("game")){
            String deckName = commands[2];
            String mode = commands[3];
            switch (mode){
                case "1":
                    Game.getInstance().setGameMode(GameMode.killingHeroOfEnemy);
                    break;
                case "2":
                    Game.getInstance().setGameMode(GameMode.collectingAndKeepingFlags);
                    break;
                case "3":
                    Game.getInstance().setGameMode(GameMode.collectingHalfOfTheFlags);
                    break;
            }
        }
    }

    public static void move(Card card, int x, int y){
        if(card.isMovable() && !card.isHasMovedInThisTurn()) {
            if (!Map.cardCanBeMovedToThisCell(card,x,y)) {
                System.out.println("Invalid target");
            } else {
                Cell.getCellByCoordination(card.getX(),card.getY()).setCellType(CellType.empty);
                card.setX(x);
                card.setY(y);
                card.setHasMovedInThisTurn(true);
                System.out.println(card.returnCompleteId(card.getName(),card.getId()) + " moved to (" +x + "," + y + ")");
                applyEffectsOfTargetCellOnCard(card, x, y);
            }
        }
        else {
            System.out.println("This card is not movable");
        }
    }

    public static void attack(Card attackerCard, Card opponentCard){

    }

    public static void applyEffectsOfTargetCellOnCard(Card card, int x, int y){
        applyCellTypeOnCard(card, x, y);
//        applyCellImpactTypeOnCard(card, x, y);
//        applyCellItemTypeOnCard(card, x, y);
    }

    public static void applyCellTypeOnCard(Card card, int x, int y){
        if (Minion.thisCardIsMinion(card.getName())){
            Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.selfMinion);
        }else if (Hero.thisCardIsHero(card.getName())){
            Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.selfHero);
        }
    }

//    public static void applyCellImpactTypeOnCard(Card card, int x, int y){
////        CellImpactType cellImpactType = Game.getInstance().getMap().getCells()[x][y].getCellImpactType();
//        Buff buff;
//        switch (cellImpactType){
//            case fire:
//                buff = new Buff("fireBuff", 2, 1);
//                CellImpactType.applyFireImpactOnCard(card, buff);
//                break;
//            case poison:
//                buff = new Buff("poisonBuff", 1, 1);
//                CellImpactType.applyPoisonImpactOnCard(card, buff);
//                break;
//            case holy:
//                CellImpactType.applyHolyImpactOnCard(card);
//                break;
//        }
//    }

//    public static void applyCellItemTypeOnCard(Card card, int x, int y){
//        CellItemType cellItemType = Game.getInstance().getMap().getCells()[x][y].getCellItemType();
//        switch (cellItemType){
//            case flag:
//                Game.getInstance().setPlayer1NumberOfFlags(Game.getInstance().getPlayer1NumberOfFlags() + 1);
//                Game.getInstance().getMap().getCells()[x][y].setCellItemType(CellItemType.empty);
//                break;
//            case collectibleItem:
//
//                break;
//        }
//    }





    public static void checkAllConditionsToAttack(Card attackerCard, Card opponentCard) throws Exception {
//        if (thisIdIsAvailableForOpponent(opponentId)) {
//            if (Hero.thisCardIsHero(cardName)) {
                if(!attackerCard.isHasAttackedInThisTurn()) {
                   attack(attackerCard, opponentCard);
                }
                else {
                    System.out.println("Hero has attacked in this turn");
//                }
//            } else {
//                if(!card.isHasAttackedInThisTurn()) {
////                checkAttackConditionsForMinion();
//                    //then attack
//                }
//                else {
//                    System.out.println("The minion has attacked in this turn");
//                }
//            }
//        }else{
//            System.out.println("opponent minion is unavailable for attack");
//        }
                }
    }






    public static boolean thisIdIsAvailableForOpponent(int id) throws Exception {
        if(Shop.checkValidId(id)){
            String cardName = returnNameById(id);
            if(cardIsInGame(cardName)){
                if(thisCardIsEnemy(cardName)){
                    return true;
                }
                else System.out.println("This card is not enemy");
            }
            else System.out.println("This card is not in game");
        }
        else {
            System.out.println("This id is not valid");
        }
        return false;
    }
    //    public static Card returnCardInGameByName(String cardName){
    //        for (Card card:
    //                Game.getInstance().getPlayer1CardsInField()) {
    //            if(card.getName().equals(cardName))
    //                return card;
    //        }
    //        for(Card card : Game.getInstance().getPlayer2CardsInField()){
    //            if(card.getName().equals(cardName))
    //                return card;
    //        }
    //        if(Game.getInstance().getHeroOfPlayer1().getName().equals(cardName)) return Game.getInstance().getHeroOfPlayer1();
    ////        if(Game.getInstance().getHeroOfPlayer2().getName().equals(checkCard)) return true;
    ////        return false;
    //        return null;

    //    }

    public static boolean checkAttackConditionsForMinion(Minion minion, int opponentCardID){
        int attackRange = minion.getAttackRange();
        return false;

    }

    public static void insertThisCardinThisCoordination(String cardName, int x, int y) throws Exception {
        int coordinateX = x - 1;
        int coordinateY = y - 1;

        if(checkIfCardIsInHandForInsertingCardsInMap(cardName, coordinateX, coordinateY)) {

            if (Minion.thisCardIsMinion(cardName)) {
                if (checkConditionsForInsertingMinionInMap(cardName, coordinateX, coordinateY)) {
                    insertMinionInThisCoordination(cardName, coordinateX, coordinateY);
                    int cardID = Minion.getMinionIDByName(cardName);
                    System.out.println(cardName + " with "+ cardID + "inserted to (" + x + "," + y + ")");
                }

            } else if (Spell.thisCardIsSpell(cardName)) {
//                Spell.insertSpellInThisCoordination(cardName, coordinateX, coordinateY);
            }
        }
    }

    public static boolean checkIfCardIsInHandForInsertingCardsInMap(String cardName, int x, int y){
        Hand hand = Game.getInstance().getPlayer1().getMainDeck().getHand();
        if (!hand.checkIfCardIsInHand(cardName)) {
            System.out.println("Invalid card name");
            return false;
        }else
            return true;
    }

    public static boolean checkConditionsForInsertingMinionInMap(String minionName, int x, int y) throws IOException, ParseException {
        if (!Map.checkIfMinionCardCanBeInsertedInThisCoordination(x, y)){
            System.out.println("Invalid target");
        }else{
            if (!playerHasEnoughManaToInsertMinion(minionName)){
                System.out.println("You don′t have enough mana");
            }else {
                return true;
            }
        }
        return false;
    }

    public static boolean playerHasEnoughManaToInsertMinion(String minionName){
        int playerMana = Game.getInstance().getPlayer1().getNumOfMana();
        int minionMana = Minion.findMinionByName(minionName).getMana();
        if (playerMana >= minionMana){
            return true;
        }else{
            return false;
        }
    }

    public static void insertMinionInThisCoordination(String minionName, int x, int y) throws IOException, ParseException {

        Card card = Game.getInstance().getPlayer1().getMainDeck().getHand().returnCardInHand(minionName);
        card.setX(x);
        card.setY(y);
        Game.getInstance().getMap().getCells()[x][y].setCellType(CellType.selfMinion);
        handleManaOfPlayerAfterInsertingCardInMap(minionName);

        Game.getInstance().getPlayer1CardsInField().add(card);

        Game.getInstance().getPlayer1().getMainDeck().getHand().removeCardFromHand(minionName);
    }

    public static void handleManaOfPlayerAfterInsertingCardInMap(String cardName) throws IOException, ParseException {
        int currentPlayerMana = Game.getInstance().getPlayer1().getNumOfMana();
        int cardMana = Card.findCardByName(cardName).getMana();
        Game.getInstance().getPlayer1().setNumOfMana(currentPlayerMana - cardMana);
    }

    public static void selectGameMode(String gameType){
        switch (gameType){
            case "killingHeroOfEnemy":
                selectFirstGameMode();
                break;
            case "collectingAndKeepingFlags":
                selectSecondGameMode();
                break;
            case "collectingHalfOfTheFlags":
                selectThirdGameMode();
                break;
        }
    }

    public static void selectFirstGameMode(){
        Game.getInstance().setGameMode(GameMode.killingHeroOfEnemy);
    }

    public static void selectSecondGameMode(){
        Game.getInstance().setGameMode(GameMode.collectingAndKeepingFlags);
      //  Item flag = Item.returnFlagByRandomCoordination();
        int x = Cell.returnRandomNumberForCoordinationInThisRange(0, 4);
        int y = Cell.returnRandomNumberForCoordinationInThisRange(0, 9);
        Game.getInstance().getMap().getCells()[x][y].setCellItemType(CellItemType.flag);

    }

    public static void selectThirdGameMode(){
        Game.getInstance().setGameMode(GameMode.collectingHalfOfTheFlags);
        for (int i = 0; i < 7; i++) {

            int x = Cell.returnRandomNumberForCoordinationInThisRange(0, 4);
            int y = Cell.returnRandomNumberForCoordinationInThisRange(0, 9);
       //     Item flag2 = new Item("flag", "collectible", 4 - x, 8 - y);
            Game.getInstance().getMap().getCells()[x][y].setCellItemType(CellItemType.flag);
            Game.getInstance().getMap().getCells()[4 - x][8 - y].setCellItemType(CellItemType.flag);
        }
        Game.getInstance().getMap().getCells()[0][0].setCellItemType(CellItemType.flag);
    }

    public static void changeTurn(){
        Game.getInstance().setNumOfRound(Game.getInstance().getNumOfRound() + 1);
        Game.getInstance().setPlayer1Turn(false);
        Game.getInstance().getMap().changeCellTypesWhenTurnChanges();

        //change all fields of player1 and player2
        Player tempPlayer = Game.getInstance().getPlayer1();
        Game.getInstance().setPlayer1(Game.getInstance().getPlayer2());
        Game.getInstance().setPlayer2(tempPlayer);

        ArrayList<Card> tempCards = new ArrayList<>(Game.getInstance().getPlayer1CardsInField());
        Game.getInstance().setPlayer1CardsInField(Game.getInstance().getPlayer2CardsInField());
        Game.getInstance().setPlayer2CardsInField(tempCards);

        Hero tempHero = Game.getInstance().getHeroOfPlayer1();
        Game.getInstance().setHeroOfPlayer1(Game.getInstance().getHeroOfPlayer2());
        Game.getInstance().setHeroOfPlayer2(tempHero);

        //Todo : change hand of the game


//        int tempNumberOfFlags = Game.getInstance().getPlayer1NumberOfFlags();
//        Game.getInstance().setPlayer1NumberOfFlags(Game.getInstance().getPlayer2NumberOfFlags());
//        Game.getInstance().setPlayer2NumberOfFlags(tempNumberOfFlags);

        Game.getInstance().setNumOfRound(Game.getInstance().getNumOfRound() + 1);

        Game.getInstance().getPlayer1().handleManaAtTheFirstOfTurn();

    }

    public static void insertCardInFieldCommand(String[] commands) throws IOException, ParseException {
        if (commands[0].equals("insert")){
            String cardName = commands[1];
            Card card = Card.findCardByName(cardName);
            int x = Integer.parseInt(commands[3].substring(1, commands[3].indexOf(',')));
            int y = Integer.parseInt(commands[3].substring(commands[3].indexOf(',') + 1));
            insertCard(card, x, y);
        }
    }

    public static void insertCard(Card card, int x, int y){

    }




}
