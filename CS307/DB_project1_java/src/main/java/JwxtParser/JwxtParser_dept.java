package JwxtParser;

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
import java.util.List;
// TODO: import the json library of your choice

public class JwxtParser_dept {

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
        Type type = new TypeToken<List<Dept>>() {
        }.getType();
        List<Dept> courses = gson.fromJson(content, type);
        System.out.println(courses.size());
        for (int j = 0; j < courses.size();j++){
            for (int i  = j + 1; i < courses.size();i++){
                if (courses.get(j).courseDept.equals(courses.get(i).courseDept))courses.remove(i--);
            }
        }
        //去重
        for (Dept d : courses) {
            d.courseDept = d.courseDept.replaceAll("\\s*", "");
        }//去除空格和制表符
        System.out.println(courses.size());
        File file = new File("dept.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        bf.write(gson.toJson(courses));
        bf.close();
        writer.close();
        System.out.println(courses.size());

        // or ...
        // ObjectMapper mapper = new ObjectMapper();
    }

//    public static String search(List<Course2> list, String className){
//        for (Course2 c : list){
//            if (c.className.equals(className))return c.courseId;
//        }
//        return null;
//    }
}

//class classList {
//    // TODO:
//    private int[] weekList;
//    public String location;
//    private String classTime;
//    private int weekday;
//}

//class class_name {
//    // TODO: define data-class as the json structure
//    public String courseId;
//    private String className;
//    private String teacher;
//    private classList[] classList;
//
//    public classList[] getClassList() {
//        return this.classList;
//    }
//
//    public String getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(String teacher) {
//        this.teacher = teacher;
//    }
//
//    public String getClassName() {
//        return className;
//    }
//
//    public void setClassName(String className) {
//        this.className = className;
//    }
//}
//class Course {
//    String courseId;
//    String courseName;
//    int totalCapacity;
//    float courseCredit;
//    int courseHour;
//    String courseDept;
//}
class Dept{
    String courseDept;
}

