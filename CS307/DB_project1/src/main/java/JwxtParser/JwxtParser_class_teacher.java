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
import java.util.*;
// TODO: import the json library of your choice

public class JwxtParser_class_teacher {

    private static final String pathToJSONFile = "teacher.json";
    private static final String pathToJSONFile2 = "/Users/lee/Downloads/course_info.json";

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
        String content2 = Files.readString(Path.of(pathToJSONFile2));
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Type type = new TypeToken<List<teacher>>() {
        }.getType();
        Type type2 = new TypeToken<List<class_info>>() {
        }.getType();
        List<teacher> courses = gson.fromJson(content, type);
        List<class_info> class_ = gson.fromJson(content2, type2);
        Map<String, String> map = new HashMap<>();
        for (teacher t : courses){
            map.put(t.teacher,t.teacher_id);
        }
        for (class_info s : class_) {
            s.teacher_id = new ArrayList<>();
        }
        int i = 1;
        for (class_info s : class_) {
            s.class_id = "Class_" + i++;
            if (s.teacher != null) {
                while (s.teacher.indexOf(",") != -1) {
                    s.teacher_id.add(map.get(s.teacher.substring(0, s.teacher.indexOf(",")).replace("\t","")));
                    s.teacher = s.teacher.substring(s.teacher.indexOf(",") + 1);
                }
                s.teacher_id.add(map.get(s.teacher.replace("\t", "")));
            }
        }
        File file = new File("class_teacher.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        bf.write(gson.toJson(class_));
        bf.close();
        writer.close();
    }

    public class teacher {
        public String teacher_id;
        public String teacher;
    }
    public class class_info{
        public String class_id;
        public String className;
        public String teacher;
        public List<String> teacher_id;
    }

}

