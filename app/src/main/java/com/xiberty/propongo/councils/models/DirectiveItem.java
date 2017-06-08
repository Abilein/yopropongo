package com.xiberty.propongo.councils.models;

import com.xiberty.propongo.database.CouncilMan;

public class DirectiveItem {
    public CouncilMan councilMan;
    public String position;

    public DirectiveItem(){
    }

    public DirectiveItem(CouncilMan councilMan, String position){
        this.councilMan = councilMan;
        this.position = position;
    }
}
