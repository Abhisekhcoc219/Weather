package com.example.weather.data.model.forecastDataModel;

public class conditionDataClass {
    private String firstCondition,secondCondtion;
    private int imageCondition;
    conditionDataClass(){

    }
    public conditionDataClass(String first_Condition, int imageCondition, String second_Condition){
     this.firstCondition=first_Condition;
     this.imageCondition=imageCondition;
     this.secondCondtion=second_Condition;
    }

    public String getFirstCondition() {
        return firstCondition;
    }

    public int getImageCondition() {
        return imageCondition;
    }

    public String getSecondCondtion() {
        return secondCondtion;
    }

    public void setFirstCondition(String firstCondition) {
        this.firstCondition = firstCondition;
    }

    public void setImageCondition(int imageCondition) {
        this.imageCondition = imageCondition;
    }

    public void setSecondCondtion(String secondCondtion) {
        this.secondCondtion = secondCondtion;
    }
}
