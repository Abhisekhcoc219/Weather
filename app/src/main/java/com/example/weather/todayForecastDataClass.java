package com.example.weather;

public class todayForecastDataClass {

    private String firstText,secondText;
    private int cloudImage;
    todayForecastDataClass(String first_text,int cloud_image,String second_text){
        this.firstText=first_text;
        this.cloudImage=cloud_image;
        this.secondText=second_text;
    }
todayForecastDataClass(){

}
    public int getCloudImage() {
        return cloudImage;
    }

    public String getFirstText() {
        return firstText;
    }

    public String getSecondText() {
        return secondText;
    }

    public void setCloudImage(int cloudImage) {
        this.cloudImage = cloudImage;
    }

    public void setFirstText(String firstText) {
        this.firstText = firstText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }
}
