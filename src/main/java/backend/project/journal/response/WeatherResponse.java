package backend.project.journal.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
    public Location location;
    public Current current;

    public class Location {
        public String name;
    }

    @Getter
    @Setter
    public class Current {
        public int temperature;
        @JsonProperty("weather_descriptions")
        public List<String> weatherDescriptions;

    }

}

