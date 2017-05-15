package com.xiberty.propongo.council.models;

import com.xiberty.propongo.db.CouncilMan;

/**
 * Created by growcallisaya on 4/5/17.
 */

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
