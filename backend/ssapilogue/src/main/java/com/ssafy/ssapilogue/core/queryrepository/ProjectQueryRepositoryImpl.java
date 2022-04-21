package com.ssafy.ssapilogue.core.queryrepository;

import com.ssafy.ssapilogue.core.domain.Category;
import com.ssafy.ssapilogue.core.domain.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProjectQueryRepositoryImpl implements ProjectQueryRepository{

    private final EntityManager em;

    @Override
    public List<Project> findAllByOrderByLikesDesc() {
        Query query = em.createQuery("select p from Project p left join Liked as l on p.id = l.project.id "
                            + "group by p.id order by count(l.project.id) desc, p.id desc");

        List<Project> projects = query.getResultList();
        return projects;
    }

    @Override
    public List<Project> findByCategoryOrderByLikesDesc(Category category) {
        Query query = em.createQuery("select p from Project p left join Liked as l on p.id = l.project.id "
                + "where p.category = ?1 "
                + "group by p.id order by count(l.project.id) desc, p.id desc")
                .setParameter(1, category);

        List<Project> projects = query.getResultList();
        return projects;
    }
}
