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
import java.util.HashSet;
import java.util.List;
// TODO: import the json library of your choice

public class JwxtParser_class {

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
        Gson gson2 = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        //填补缺失值为null
        Type type = new TypeToken<List<class_>>() {
        }.getType();
        List<class_> courses = gson.fromJson(content, type);
        System.out.println(courses.size());
        for (class_ c : courses) {
                c.className = c.className.replaceAll("\\s*", "");
                c.courseId = c.courseId.replaceAll("\\s*", "");
        }//去除空格和制表符
        File file = new File("class.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        System.out.println(courses.size());
        bf.write(gson.toJson(courses));
        bf.close();
        writer.close();
    }

    public class class_ {
        public String className;
        public String courseId;
        public int totalCapacity;
    }

}

