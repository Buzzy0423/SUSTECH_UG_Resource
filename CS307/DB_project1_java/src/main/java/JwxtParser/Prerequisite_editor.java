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
import java.util.*;
// TODO: import the json library of your choice

public class Prerequisite_editor {

    private static final String pathToJSONFile = "Pre.json";
    private static final String pathToJSONFile2 = "Course_info.json";

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
        // Gson is an example
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        //填补缺失值为null
        Type type2 = new TypeToken<List<Course>>() {
        }.getType();
        Type type = new TypeToken<List<Pre_course>>() {
        }.getType();
        List<Pre_course> courses = gson.fromJson(content, type);
        List<Course> courses2 = gson.fromJson(content2, type2);
        Map<String, String> Id = new HashMap<>();
        for (Course c : courses2) {
            Id.put(c.courseName, c.courseId);
        }
        List<Pre_course> pc = new ArrayList<>();
//        for (Pre p : courses) {
//            if (p.prerequisite == null) continue;
//            int num = 1;
//            boolean quote = false;
//            boolean check = false;
//            for (int i = 0; i < p.prerequisite.length(); i++) {
//                if (p.prerequisite.charAt(i) == 44) { /* , */
//                    if (!quote) num++;
//                } else if (p.prerequisite.charAt(i) == 91) { /* [ */
//                    quote = true;
//                } else if (p.prerequisite.charAt(i) == 93) { /* ] */
//                    quote = false;
//                    if (!check) num--;
//                    check = false;
//                } else if (p.prerequisite.charAt(i) == 34) { /* " */
//                    int tmp = i++;
//                    while (p.prerequisite.charAt(i) != 34) i++;
//                    if (course_name.contains(p.prerequisite.substring(tmp + 1, i))) {
//                        if (quote) check = true;
//                        pre_courses.add(new Pre_course(p.courseId, num, p.prerequisite.substring(tmp + 1, i)));
//                    }
//                }
//            }
//        }
        for (Pre_course preCourse : courses) {
            pc.add(new Pre_course(preCourse.courseId, preCourse.group_num, Id.get(preCourse.course)));
        }
        for (int j = 0; j < pc.size(); j++) {
            for (int i = j + 1; i < pc.size(); i++) {
                if (pc.get(j) != null && pc.get(j) != null) {
                    if (pc.get(i).course.equals(pc.get(j).course)
                            && pc.get(i).courseId.equals(pc.get(j).courseId)
                            && pc.get(i).group_num == pc.get(j).group_num){
                        pc.remove(i--);
                    }
                }
            }
        }
        System.out.println(pc.size());
        File file = new File("Pre2.json");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(writer);
        bf.write(gson.toJson(pc));
        bf.close();
        writer.close();
    }


    class Pre {
        String courseId;
        String prerequisite;
    }

    static class Pre_course {
        String courseId;
        int group_num;
        String course;

        Pre_course(String courseId, int group_num, String course) {
            this.courseId = courseId;
            this.group_num = group_num;
            this.course = course;
        }
    }

    class Course {
        String courseId;
        String courseName;
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

