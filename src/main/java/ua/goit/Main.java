package ua.goit;

import org.reflections.Reflections;
import ua.goit.model.*;
import ua.goit.repository.*;
import ua.goit.service.EntityServiceImpl;
import ua.goit.service.Reporter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        BaseRepository<Integer, Company> companyRepository = new CompanyRepository();
        EntityServiceImpl<Integer,Company> companyService = new EntityServiceImpl<>(companyRepository);
        companyService.create(new Company(11,"New Solutions","77775555"));
        System.out.println(companyService.read(11));
        companyService.update(11, new Company(11,"SofTeena","12345678"));
        System.out.println(companyService.read(11));
        companyService.delete(11);

        BaseRepository<Integer, Customer> customerRepository = new CustomerRepository();
        EntityServiceImpl<Integer,Customer> customerService = new EntityServiceImpl<>(customerRepository);
        customerService.create(new Customer(11,"New CUSTOMER","23423423"));
        System.out.println(customerService.read(11));
        customerService.update(11, new Customer(11,"BEST CHANCE","90909090"));
        System.out.println(customerService.read(11));
        customerService.delete(11);

        BaseRepository<Integer,Developer> developerRepository = new DeveloperRepository();
        EntityServiceImpl<Integer,Developer> developerService = new EntityServiceImpl<>(developerRepository);
        developerService.create(new Developer(13,"Maria",31,"F",3100,
                companyService.read(3)));
        System.out.println(developerService.read(13));
        developerService.update(13,new Developer(13,"Sintia",31,"F",3100,
                companyService.read(3)));
        System.out.println(developerService.read(13));
        developerService.delete(13);

        BaseRepository<Integer, Project> projectRepository = new ProjectRepository();
        EntityServiceImpl<Integer,Project> projectService = new EntityServiceImpl<>(projectRepository);
        projectService.create(new Project(111,"MEGA PROJECT", 1_000_000,
                companyService.read(2),
                customerService.read(1)
        ));
        System.out.println(projectService.read(111));
        projectService.update(111, new Project(111,"MEGA PROJECT 2.0",1_200_000,
                companyService.read(2),
                customerService.read(1)
        ));
        System.out.println(projectService.read(111));
        projectService.delete(111);

        BaseRepository<Integer, Skill> skillRepository = new SkillRepository();
        EntityServiceImpl<Integer,Skill> skillService = new EntityServiceImpl<>(skillRepository);
        skillService.create(new Skill(11,"Fortran 77","Senior"));
        System.out.println(skillService.read(11));
        skillService.update(11, new Skill(11,"Borland Pascal","Middle"));
        System.out.println(skillService.read(11));
        skillService.delete(11);

        Reporter reporter = new Reporter();
        reporter.printReportSalaryOfProject(5);
        reporter.printReportDevelopersOfProject(2);
        reporter.printReportDevelopersBySkill("Java");
        reporter.printReportDevelopersByLevel("Middle");
        reporter.printReportProjectList();
    }

    public void printModelsAndFields() {
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
            System.out.println(declaredFields.toString());
        }
    }
}
