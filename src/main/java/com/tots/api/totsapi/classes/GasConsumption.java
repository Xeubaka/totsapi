package com.tots.api.totsapi.classes;

import java.util.Comparator;

public class GasConsumption implements Comparable<GasConsumption> {
    private String name;
    private String model;
    private String year;
    private Long gasConsumption;
    private Long gasSpent;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public Long getGasConsumption() {
        return gasConsumption;
    }
    public void setGasConsumption(Long gasConsumption) {
        this.gasConsumption = gasConsumption;
    }
    public Long getGasSpent() {
        return gasSpent;
    }
    public void setGasSpent(Long gasSpent) {
        this.gasSpent = gasSpent;
    }
    
    @Override
    public int compareTo(GasConsumption GasConsumption) {
        Long comparison = ((GasConsumption) GasConsumption).getGasSpent();
        return comparison.intValue() - this.gasSpent.intValue();
    }

    public static Comparator<GasConsumption> GasConsumptionComparator 
                          = new Comparator<GasConsumption>() {

	    public int compare(GasConsumption x, GasConsumption y) {	    	
	      Long gasSpendX = x.getGasSpent();
	      Long gasSpendY = y.getGasSpent();
	      return gasSpendY.compareTo(gasSpendX);
	    }

	};
}
