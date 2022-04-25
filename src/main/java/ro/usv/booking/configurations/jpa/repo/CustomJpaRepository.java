package ro.usv.booking.configurations.jpa.repo;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ro.usv.booking.utils.Audit;

import javax.persistence.EntityManager;
import java.util.function.Function;

public class CustomJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager em;

    public CustomJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
    }

    public CustomJpaRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
    }

    @Transactional
    @Override
    public boolean softDelete(ID id) {
        Assert.notNull(id, "The given id must not be null!");
        T entity = this.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", getDomainClass(), id), 1));
        if (!(entity instanceof Audit))
            throw new IllegalArgumentException("Entity does not extend Audit");

        ((Audit) entity).setIsDeleted(true);
        return ((Audit) this.save(entity)).getIsDeleted();
    }

    @Override
    public T refresh(T t) {
        em.refresh(t);
        return t;
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {

    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
