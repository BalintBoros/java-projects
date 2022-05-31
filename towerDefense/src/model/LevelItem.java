package model;

public enum LevelItem {
    GRASS('*'), BASE1('€'), BASE2('$'), LAKE('O'), TOWER1('#'), TOWER2('&'), 
    TOWER3('@'), TREES('T'), MINE('M'), B1UNIT('Q'), F1UNIT('W'), S1UNIT('E'),
    B2UNIT('A'), F2UNIT('S'), S2UNIT('D') , T1UNIT('R'), T2UNIT('F');
    LevelItem(char rep){ representation = rep; }
    public final char representation;
}
