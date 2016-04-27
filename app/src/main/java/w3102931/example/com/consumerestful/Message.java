package w3102931.example.com.consumerestful;

import android.location.Location;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by mscs on 4/21/16..
 *     long id;
 String message;
 */
//public class Message implements Serializable{

    public class Message {


    public long id;
    public double latitude;
    public double longitude;
    public long timestamp;


        public Message() {

        }


        public Message(long id, double latitude, double longitude, long timestamp) {

            this.id = id;
            this.latitude = latitude;
            this.longitude = longitude;
            this.timestamp = timestamp;
            //this.timestamp = timestamp == null ? System.currentTimeMillis() : timestamp;

        }

        public long getId() {
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


        public void setId(long id) {
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






        public static Message message(long id, double latitude, double longitude, long timestamp) {
            return new Location(id, message.latitude, message.longitude, message.timestamp);
    }

}





