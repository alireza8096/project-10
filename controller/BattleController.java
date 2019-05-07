package controller;

import model.*;
import model.collection.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public interface BattleController {
    public static void selectCardById(String[] commands, Scanner scanner) throws Exception{
        if(commands.length == 2 && commands[0].compareToIgnoreCase("select") == 0
                && commands[1].matches("[\\d]+")) {
            int id = Integer.parseInt(commands[1]);
            if (Shop.checkValidId(id)) {
                String cardName = returnNameById(id);
                if (cardIsInGame(cardName)) {
                    if (thisCardIsYours(cardName)) {
                        doActionOnCard(cardName, scanner);
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
    public static void moveToXY(String[] commands,String cardName){
        if(commands.length == 3 && commands[0].compareToIgnoreCase("move") == 0
                && commands[1].compareToIgnoreCase("to") == 0
                && commands[2].matches("\\([\\d]+,[\\d]+\\)")) {
            checkAllConditionsToMoveCard(commands[2], cardName);
            AllDatas.didAction = true;
        }
    }
    public static void attack(String[] commands,String cardName) throws Exception {
        if(commands.length == 2 && commands[0].compareToIgnoreCase("attack") == 0
                && commands[1].matches("[\\d]+")) {
            checkAllConditionsToAttack(commands[1], cardName);
            AllDatas.didAction = true;
        }
    }
    public static void attackCombo(String[] commands,String cardName){
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
    public static void useSpecialPowerXY(String[] commands,String cardName) throws IOException, ParseException {
        if (commands.length == 4 && commands[0].compareToIgnoreCase("use") == 0 &&
                commands[1].compareToIgnoreCase("special") == 0 && commands[2].compareToIgnoreCase("power") == 0 &&
                commands[3].matches("\\([\\d]+,[\\d]+\\)")) {
            checkConditionsToApplySpecialPower(commands[3], cardName);
            AllDatas.didAction = true;
        }
    }


    public static String returnNameById(int cardId)throws Exception{
        int idType = cardId/100;
        int id = cardId%100;
        String name = "";
        switch (idType){
            case 1:
                name = Hero.findHeroNameByID(id);
                break;
            case 2:
                name = Item.findItemNameByID(id);
                break;
            case 3:
                name = Minion.findMinionNameByID(id);
                break;
            case 4:
                name = Spell.findSpellNameByID(id);
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
    public static void doActionOnCard(String cardName,Scanner scanner) throws Exception {
        String command = scanner.nextLine();
        String[] commands = command.split(" ");
        moveToXY(commands,cardName);
        attack(commands,cardName);
        attackCombo(commands,cardName);
        useSpecialPowerXY(commands,cardName);
        if(!AllDatas.didAction){
            System.out.println("Command was not supported");
        }
    }
    public static void checkConditionsToApplySpecialPower(String command,String cardName) throws IOException, ParseException {
        int i = command.indexOf(",");
        int x = Integer.parseInt(command.substring(1,i)) - 1;
        int y = Integer.parseInt(command.substring(i+1,command.length()-1))-1;
        if(Hero.thisCardIsHero(cardName)){
            Hero hero = (Hero)Hero.getCardByName(cardName);
            if(!hero.getSpecialPower().matches("Does not have special power")){
                if(Game.getInstance().getPlayer1().getNumOfMana() >= hero.getMana()){
                    //apply special power
                }
                else{
                    System.out.println("Your mana is not enough to use hero's special power");
                }
            }
            else{
                System.out.println("This hero does not have special power");
            }
        }
        else{
            Minion minion = (Minion) Minion.getMinionByName(cardName);
            if(!minion.getSpecialPower().matches("Does not have special power")){
                if(Game.getInstance().getPlayer1().getNumOfMana() >= minion.getMana()) {
                    //apply special power
                }
                else {
                    System.out.println("Your mana is not enough to use minion's special power");
                }
            }
            else{
                System.out.println("This minion does not have special power");
            }
        }
    }
    public static void checkAllConditionsToMoveCard(String command, String cardName){
        int i = command.indexOf(",");
        int x = Integer.parseInt(command.substring(1,i)) - 1;
        int y = Integer.parseInt(command.substring(i+1,command.length()-1))-1;

        for (Card check : Game.getInstance().getPlayer1CardsInField()) {
            if(check.getName().equals(cardName)) {
                move(check, x, y);
            }
        }
        if(Game.getInstance().getHeroOfPlayer1().getName().equals(cardName)){
            move(Game.getInstance().getHeroOfPlayer1(), x, y);
        }
    }
    public static void move(Card card,int x,int y){
        if(card.isMovable() && !card.isHasMovedInThisTurn()) {
            if (!Map.cardCanBeMovedToThisCell(card,x,y)) {
                System.out.println("Invalid target");
            } else {
                Cell.getCellByCoordination(card.getX(),card.getY()).setCellType(CellType.empty);
                card.setX(x);
                card.setY(y);
                card.setHasMovedInThisTurn(true);
                System.out.println(card.returnCompleteId(card.getName(),card.getId()) + " moved to (" +x + "," + y + ")");
            }
        }
        else {
            System.out.println("This card is not movable");
        }
    }

    public static void checkAllConditionsToAttack(String command,String cardName) throws Exception {
        int opponentId = Integer.parseInt(command);
        Card card = Card.getCardByName(cardName);
        if (thisIdIsAvailableForOpponent(opponentId)) {
            if (Hero.thisCardIsHero(cardName)) {
                if(!card.isHasAttackedInThisTurn()) {
//                attack();
                }
                else {
                    System.out.println("Hero has attacked in this turn");
                }
            } else {
                if(!card.isHasAttackedInThisTurn()) {
//                checkAttackConditionsForMinion();
                    //then attack
                }
                else {
                    System.out.println("The minion has attacked in this turn");
                }
            }
        }else{
            System.out.println("opponent minion is unavailable for attack");
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

//    public static boolean checkAttackConditionsForMinion(Minion minion, int opponentCardID){
//        int attackRange = minion.getAttackRange();
//
//
//    }

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
                Spell.insertSpellInThisCoordination(cardName, coordinateX, coordinateY);
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

    public static boolean playerHasEnoughManaToInsertMinion(String minionName) throws IOException, ParseException {
        int playerMana = Game.getInstance().getPlayer1().getNumOfMana();
        int minionMana = Minion.getMinionByName(minionName).getMana();
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
        int cardMana = Card.getCardByName(cardName).getMana();
        Game.getInstance().getPlayer1().setNumOfMana(currentPlayerMana - cardMana);
    }

    static void selectGameMode(String gameType){
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

    static void selectFirstGameMode(){
        Game.getInstance().setGameMode(GameMode.killingHeroOfEnemy);
    }

    static void selectSecondGameMode(){
        Game.getInstance().setGameMode(GameMode.collectingAndKeepingFlags);
      //  Item flag = Item.returnFlagByRandomCoordination();
        int x = Cell.returnRandomNumberForCoordinationInThisRange(0, 4);
        int y = Cell.returnRandomNumberForCoordinationInThisRange(0, 9);
        Game.getInstance().getMap().getCells()[x][y].setCellItemType(CellItemType.flag);

    }

    static void selectThirdGameMode(){
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

    static void changeTurn(){
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

        int tempNumberOfFlags = Game.getInstance().getPlayer1NumberOfFlags();
        Game.getInstance().setPlayer1NumberOfFlags(Game.getInstance().getPlayer2NumberOfFlags());
        Game.getInstance().setPlayer2NumberOfFlags(tempNumberOfFlags);


    }

}
