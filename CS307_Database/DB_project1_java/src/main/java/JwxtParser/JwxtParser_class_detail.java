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

public class JwxtParser_class_detail {

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
        List<class_detail> tmp = new ArrayList<>();
        System.out.println(courses.size());
        int i = 1;
        for (loc loc : courses) {
            for (classList c : loc.classList) {
                if (c != null && c != null) {
                    String t = "Class_" + i;
                    c.location = c.location.replaceAll("\\s*", "");
                    tmp.add(new class_detail(t,c.weekList,c.location,c.classTime,c.weekday));
                }
            }
            i++;
        }//去除空格和制表符
        System.out.println(tmp.size());
        File file = new File("class_detail.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        bf.write(gson.toJson(tmp));
        bf.close();
        writer.close();
    }

    public class loc {
        public classList[] classList;
    }

    public class classList {
        int[] weekList;
        String location;
        String classTime;
        int weekday;
    }

    public static class class_detail {
        String class_id;
        int[] weekList;
        String location;
        String classTime;
        int weekday;

        class_detail(String class_id, int[] weekList, String location, String classTime, int weekday) {
            this.class_id = class_id;
            this.weekList = weekList;
            this.classTime = classTime;
            this.location = location;
            this.weekday = weekday;
        }
    }
}

