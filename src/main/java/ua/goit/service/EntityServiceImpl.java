//package ua.goit.service;
//
//import ua.goit.model.BaseEntity;
//import ua.goit.repository.BaseRepository;
//
//public class EntityServiceImpl<ID, E extends BaseEntity<ID>> implements EntityService<ID,E>{
//
//    private final BaseRepository<ID, E> repository;
//
//    public EntityServiceImpl(BaseRepository<ID, E> repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public void create(E e) {
//        repository.save(e);
//    }
//
//    @Override
//    public E read(ID id) {
//        return repository.getOne(id);
//    }
//
//    @Override
//    public E update(ID id, E e) {
//        //repository.update(id, e);
//        return repository.getOne(id);
//    }
//
//    @Override
//    public void delete(ID id) {
//        repository.deleteById(id);
//    }
//}
