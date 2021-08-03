package ua.goit;

import org.reflections.Reflections;
import ua.goit.model.Company;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.CompanyRepository;
import ua.goit.service.EntityServiceImpl;
import ua.goit.service.Reporter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
//        Reflections reflections = new Reflections("ua.goit");
//        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Entity.class);
//        for (Class cl: typesAnnotatedWith){
//            System.out.println(cl.getName());
//            Field[] declaredFields = cl.getDeclaredFields();
//            for (Field f: declaredFields) {
//                System.out.println(" -- : " + f.getName());
//                Column[] annotationsByType = f.getAnnotationsByType(Column.class);
//                //System.out.println(Arrays.toString(annotationsByType));
//            }
//            System.out.println(declaredFields.toString());
//        }

        Company company = new Company(10,"New Solutions","77775555");
        BaseRepository<Integer, Company> companyRepository = new CompanyRepository();
        EntityServiceImpl<Integer,Company> companyService = new EntityServiceImpl<>(companyRepository);
        //companyService.create(company);

        Company read = companyService.read(1);
        System.out.println(read);

        new Reporter().printReport(5);
    }

}
