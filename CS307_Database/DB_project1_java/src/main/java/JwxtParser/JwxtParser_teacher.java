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

public class JwxtParser_teacher {

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
        Type type = new TypeToken<List<teacher>>() {
        }.getType();
        List<teacher> courses = gson.fromJson(content, type);
        List<String> t = new ArrayList<>();
        System.out.println(courses.size());
        for (teacher s : courses) {
            if (s.teacher != null) {
                while (s.teacher.indexOf(",") != -1) {
                    t.add(s.teacher.substring(0, s.teacher.indexOf(",")));
                    s.teacher = s.teacher.substring(s.teacher.indexOf(",") + 1);
                }
                t.add(s.teacher);
            }
        }
        for (int j = 0; j < t.size(); j++) {
            for (int i = j + 1; i < t.size(); i++) {
                if (t.get(j) != null && t.get(j) != null) {
                    if (t.get(j).equals(t.get(i))) t.remove(i--);
                }
            }
        }
        int i = 1;
        List<teacher2> tt = new ArrayList<>();
        for (String s: t){
            String tmp = "Teacher_" + i++;
            tt.add(new teacher2(tmp,s));
        }
        File file = new File("teacher.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        System.out.println(t.size());
        bf.write(gson.toJson(tt));
        bf.close();
        writer.close();
    }

    public class teacher {
        public String teacher;
    }

    public static class teacher2 {
        public String teacher_id;
        public String teacher;

        teacher2(String teacher_id, String teacher) {
            this.teacher_id = teacher_id;
            this.teacher = teacher;
        }
    }

}

