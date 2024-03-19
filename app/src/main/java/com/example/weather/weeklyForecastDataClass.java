package com.example.weather;

public class weeklyForecastDataClass {
    private String firstText,secondText;
    private int cloudImage;
    weeklyForecastDataClass(String first_Text,String second_Text,int cloud_Image){
        this.firstText=first_Text;
        this.secondText=second_Text;
        this.cloudImage=cloud_Image;
    }
    weeklyForecastDataClass(){

    }
    public String getFirstText() {
        return firstText;
    }

    public int getCloudImage() {
        return cloudImage;
    }

    public String getSecondText() {
        return secondText;
    }

    public void setFirstText(String firstText) {
        this.firstText = firstText;
    }

    public void setCloudImage(int cloudImage) {
        this.cloudImage = cloudImage;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }
}
