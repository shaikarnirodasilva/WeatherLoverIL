package kds.shai.weatherlover;

public class CityExpense {
    private Integer int_id = null;
    private String cityName;

    public CityExpense(Integer int_id, String cityName) {
        this.int_id = int_id;
        this.cityName = cityName;
    }

    public CityExpense(String cityName) {
        this.cityName = cityName;
    }

    public Integer getInt_id() {
        return int_id;
    }

    public void setInt_id(Integer int_id) {
        this.int_id = int_id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "CityExpense{" +
                "int_id=" + int_id +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
