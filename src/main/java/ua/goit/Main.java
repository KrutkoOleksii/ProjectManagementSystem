package ua.goit;

import org.reflections.Reflections;
import ua.goit.model.Company;
import ua.goit.repository.BaseRepository;
import ua.goit.repository.CompanyRepository;
import ua.goit.service.EntityServiceImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.lang.reflect.Field;
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
                //System.out.println(Arrays.toString(annotationsByType));
            }
            System.out.println(declaredFields.toString());
        }
        Company company = new Company(10,"New Solutions","77775555");
        BaseRepository<Integer, Company> companyRepository = new CompanyRepository();
        EntityServiceImpl<Integer,Company> companyService = new EntityServiceImpl<>(companyRepository);
        //companyService.create(company);

        Company read = companyService.read(1);
        System.out.println(read);


    }

    public void salaryOfProject(Integer id) {
        //     * зарплату(сумму) всех разработчиков отдельного проекта
        String queryText = "SELECT project_name, sum(salary) as salary " +
                " FROM developers " +
                " INNER JOIN developer_project " +
                " ON developer_project.developer_id = developers.developer_id " +
                " INNER JOIN projects " +
                " ON developer_project.project_id = projects.project_id " +
                " WHERE projects.project_id = " + id +
                " GROUP BY project_name";
    }

    public void developersOfProject(Integer id) {
        //     * список разработчиков отдельного проекта
        String queryText = "SELECT" +
                " developer_name" +
                " FROM developers" +
                " INNER JOIN developer_project" +
                " ON developer_project.developer_id = developers.developer_id" +
                " INNER JOIN projects" +
                " ON developer_project.project_id = projects.project_id" +
                " WHERE projects.project_id = " + id;
    }

    public void developersJava(String skill) {
        //     * список всех Java разработчиков
        String queryText = "SELECT" +
                " developer_name" +
                " FROM developers" +
                " INNER JOIN developer_skill" +
                " ON developer_skill.developer_id = developers.developer_id" +
                " INNER JOIN skills" +
                " ON developer_skill.skill_id = skills.skill_id" +
                " WHERE skills.skill_name = " + skill; // if skill = 'Java'
    }

    public void developersMiddle(String level) {
        //     * список всех Middle разработчиков
        String queryText = "SELECT" +
                " developer_name" +
                " FROM developers" +
                " INNER JOIN developer_skill" +
                " ON developer_skill.developer_id = developers.developer_id" +
                " INNER JOIN skills" +
                " ON developer_skill.skill_id = skills.skill_id" +
                " WHERE skills.skill_level = " + level; // if level = 'Middle'
    }

    // список проектов в следующем формате:
    // дата создания - название проекта - количество разработчиков на этом проекте.
    public void projectList(String level) {
        //     * список всех Middle разработчиков
        String queryText = "SELECT" +
                " project_name" +
                " FROM developers" +
                " INNER JOIN developer_project" +
                " ON developer_project.developer_id = developers.developer_id" +
                " INNER JOIN projects" +
                " ON developer_project.project_id = projects.project_id";
    }

}
