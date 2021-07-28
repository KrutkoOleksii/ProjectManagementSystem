package ua.goit.service;

import org.reflections.Reflections;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Reflections reflections = new Reflections("ua.goit");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Entity.class);
        for (Class cl: typesAnnotatedWith){
            System.out.println(cl.getName());
            Field[] declaredFields = cl.getDeclaredFields();


            for (Field f: declaredFields) {
                System.out.println(" -- : " + f.getName());
                Column[] annotationsByType = f.getAnnotationsByType(Column.class);
                System.out.println(Arrays.toString(annotationsByType));
            }
        }

    }
}
