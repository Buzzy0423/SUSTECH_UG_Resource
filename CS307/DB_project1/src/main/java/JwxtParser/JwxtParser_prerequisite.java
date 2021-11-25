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
import java.util.HashSet;
import java.util.List;
// TODO: import the json library of your choice

public class JwxtParser_prerequisite {

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
        // Gson is an example
//        Gson gson  = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        Gson gson2 = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        //填补缺失值为null
        Type type = new TypeToken<List<Prerequisite>>() {
        }.getType();
//        Type type2 = new TypeToken<List<Course2>>() {
//        }.getType();
        List<Prerequisite> courses = gson.fromJson(content, type);
//        List<Prerequisite> courses2 = gson.fromJson(content, type2);
        System.out.println(courses.size());
//        for (Prerequisite p : courses) {
//
//        }
        for (int j = 0; j < courses.size();j++){
            for (int i  = j + 1; i < courses.size();i++){
                if (courses.get(j).courseId.equals(courses.get(i).courseId))courses.remove(i--);
            }
        }
        //去重
//        for (class_name c : courses) {
//            c.getClassList()[0].location = c.getClassList()[0].location.replaceAll("\\s*", "");
//            if (c.getTeacher() != null) c.setTeacher(c.getTeacher().replaceAll("\\s*", ""));
//            c.setClassName(c.getClassName().replaceAll("\\s*", ""));
//            c.courseId = c.courseId.replaceAll("\\s*", "");
//        }//去除空格和制表符
//        System.out.println(courses.size());
        for (Prerequisite p : courses){
            p.courseId = p.courseId.replaceAll("\\s*", "");
            if (p.prerequisite != null){
                p.prerequisite = clean(p.prerequisite);
            }
        }
        File file = new File("Prerequisite.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        bf.write(gson.toJson(courses));
        bf.close();
        writer.close();
//        System.out.println(gson.toJson(courses));
//        System.out.println(courses.size());

        // or ...
        // ObjectMapper mapper = new ObjectMapper();
    }

//    public static String search(List<Course2> list, String className){
//        for (Course2 c : list){
//            if (c.className.equals(className))return c.courseId;
//        }
//        return null;
//    }

    public static String clean(String s) {
        s = s.replace(" 或者 ", ",");
        s = s.replace(" 并且 ", ",");
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 40 && s.charAt(i + 2) == 41) continue;
            if (s.charAt(i) == 41 && s.charAt(i - 2) == 40) continue;
            if (s.charAt(i) == 41) s = s.substring(0, i) + "]" + s.substring(i + 1);
            if (s.charAt(i) == 40) s = s.substring(0, i) + "[" + s.substring(i + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) == 44 | s.charAt(i) == 91) && s.charAt(i + 1) != 91) {
                s = s.substring(0, i + 1) + "\"" + s.substring(i + 1);
            }
            if (i == 0 && s.charAt(i) != 91){
                s = "\"" + s;
                i++;
            }
            if ((s.charAt(i) == 44 | s.charAt(i) == 93) && s.charAt(i - 1) != 93) {
                s = s.substring(0, i) + "\"" + s.substring(i);
                i++;
            }
            if (i == s.length() - 1 && s.charAt(i) != 93){
                s = s + "\"";
                i++;
            }
        }
        return s;
    }
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
//    private int totalCapacity;
//    private String courseId;
//    private String courseName;
//    private float courseCredit;
//    private int courseHour;
//    private String courseDept;
//    public String getCourseId(){
//        return courseId;
//    }
//
//    public void setCourseId(String courseId) {
//        this.courseId = courseId;
//    }
//}
//class Course2 {
//    public String courseId;
//    public String className;
//}

