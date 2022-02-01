package org.movium.services;

import org.movium.model.BoxOfficeEntity;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ConversionService {

    public List<BoxOfficeEntity> convertToZAR(double conversionRate, List<BoxOfficeEntity> boxOfficeEntityList){
        convertGross(conversionRate, boxOfficeEntityList);
        convertWeekend(conversionRate, boxOfficeEntityList);
        return boxOfficeEntityList;
    }

    private void convertWeekend(double conversionRate, List<BoxOfficeEntity> boxOfficeEntityList) {
        for(BoxOfficeEntity boxOfficeEntity: boxOfficeEntityList){
            String randValue = "";
            var a = boxOfficeEntity.getWeekend().split("\\$");
            var b =new String[2];
            if(a[1].contains("K")){
                b = a[1].split("K");
            }else {
                b = a[1].split("M");
            }
            var dollar = Double.parseDouble(b[0]);
            var rand = dollar * conversionRate;
            var randRound = Math.round(rand*100.0)/100.0;
            randValue = "ZAR "+ Double.toString(randRound)+"M";
            boxOfficeEntity.setWeekend(randValue);

        }
    }

    private void convertGross(double conversionRate, List<BoxOfficeEntity> boxOfficeEntityList) {
        for(BoxOfficeEntity boxOfficeEntity: boxOfficeEntityList){
            String randValue = "";
            var a = boxOfficeEntity.getGross().split("\\$");
            var b = a[1].split("M");
            var dollar = Double.parseDouble(b[0]);
            var rand = dollar * conversionRate;
            var randRound = 0.0;
           if(rand > 999.999){
               rand = rand /1000;
               randRound = Math.round(rand*100.0)/100.0;
               randValue = "ZAR "+ Double.toString(randRound)+"B";
           }else {
               randRound = Math.round(rand*100.0)/100.0;
               randValue = "ZAR "+ Double.toString(randRound)+"M";
           }
            boxOfficeEntity.setGross(randValue);
        }
    }
}
