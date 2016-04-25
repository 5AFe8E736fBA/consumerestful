package w3102931.example.com.consumerestful;

/**
 * Created by mscs on 4/21/16.
 *     long id;
 String message;
 */
//public class Message implements Serializable{

    public class Message {


    public String id;
    public double latitude;
    public double longitude;
    public long timestamp;


        public Message() {

        }


        public Message(String id, double latitude, double longitude, long timestamp) {

            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.timestamp = timestamp;
            //this.timestamp = timestamp == null ? System.currentTimeMillis() : timestamp;

        }

        public String getId() {
            return this.id;
        }

        public double getLatitude() {
            return this.latitude;
        }

        public double getLongitude() {
            return this.longitude;
        }

        public long gettimestamp() {
            return this.timestamp;
        }


        public void setId(String id) {
            this.id = id;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

         public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

}






