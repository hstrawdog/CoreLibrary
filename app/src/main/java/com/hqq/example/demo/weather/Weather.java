package com.hqq.example.demo.weather;

import java.util.List;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo.weather
 * @FileName :   Weather
 * @Date : 2020/8/4 0004  下午 4:40
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class Weather {

    /**
     * reason : 查询成功!
     * result : {"city":"福州","realtime":{"temperature":"30","humidity":"66","info":"阴","wid":"02","direct":"西南风","power":"5级","aqi":"24"},"future":[{"date":"2020-08-04","temperature":"27/35℃","weather":"小雨转多云","wid":{"day":"07","night":"01"},"direct":"南风转持续无风向"},{"date":"2020-08-05","temperature":"26/34℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"南风转持续无风向"},{"date":"2020-08-06","temperature":"26/36℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"},{"date":"2020-08-07","temperature":"26/37℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"},{"date":"2020-08-08","temperature":"27/37℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * city : 福州
         * realtime : {"temperature":"30","humidity":"66","info":"阴","wid":"02","direct":"西南风","power":"5级","aqi":"24"}
         * future : [{"date":"2020-08-04","temperature":"27/35℃","weather":"小雨转多云","wid":{"day":"07","night":"01"},"direct":"南风转持续无风向"},{"date":"2020-08-05","temperature":"26/34℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"南风转持续无风向"},{"date":"2020-08-06","temperature":"26/36℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"},{"date":"2020-08-07","temperature":"26/37℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"},{"date":"2020-08-08","temperature":"27/37℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"}]
         */

        private String city;
        private RealtimeBean realtime;
        private List<FutureBean> future;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public RealtimeBean getRealtime() {
            return realtime;
        }

        public void setRealtime(RealtimeBean realtime) {
            this.realtime = realtime;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class RealtimeBean {
            /**
             * temperature : 30
             * humidity : 66
             * info : 阴
             * wid : 02
             * direct : 西南风
             * power : 5级
             * aqi : 24
             */

            private String temperature;
            private String humidity;
            private String info;
            private String wid;
            private String direct;
            private String power;
            private String aqi;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getWid() {
                return wid;
            }

            public void setWid(String wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }
        }

        public static class FutureBean {
            /**
             * date : 2020-08-04
             * temperature : 27/35℃
             * weather : 小雨转多云
             * wid : {"day":"07","night":"01"}
             * direct : 南风转持续无风向
             */

            private String date;
            private String temperature;
            private String weather;
            private WidBean wid;
            private String direct;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WidBean getWid() {
                return wid;
            }

            public void setWid(WidBean wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public static class WidBean {
                /**
                 * day : 07
                 * night : 01
                 */

                private String day;
                private String night;

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public String getNight() {
                    return night;
                }

                public void setNight(String night) {
                    this.night = night;
                }
            }
        }
    }
}
