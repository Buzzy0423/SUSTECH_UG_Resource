package JwxtParser;

import GoodLoader.GoodLoader_detail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
// TODO: import the json library of your choice

public class JwxtParser_location {

    private static final String pathToJSONFile = "/Users/lee/Downloads/course_info.json";

    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static void main(String[] args) throws IOException {
        String content = Files.readString(Path.of(pathToJSONFile));
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        //填补缺失值为null
        Type type = new TypeToken<List<loc>>() {
        }.getType();
        List<loc> courses = gson.fromJson(content, type);
        List<String> tmp = new ArrayList<>();
        System.out.println(courses.size());
        for (loc loc : courses) {
            for (classList c : loc.classList){
                if (c != null && c != null){
                    c.location = c.location.replaceAll("\\s*", "");
                    tmp.add(c.location);
                }
            }
        }//去除空格和制表符
        for (int j = 0; j < tmp.size();j++){
            for (int i  = j + 1; i < tmp.size();i++){
                if (tmp.get(j) != null && tmp.get(j) != null) {
                    if (tmp.get(j).equals(tmp.get(i))) tmp.remove(i--);
                }
            }
        }
        //去重
        System.out.println(tmp.size());
        File file = new File("location.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        bf.write(gson.toJson(tmp));
        bf.close();
        writer.close();
    }

    public class loc{
         public classList[] classList;
    }

    public class classList{
        String location;
    }
}

