# rulz
Reusable JUnit Rules


##JPA EntityManager

```java
import com.airhacks.rulz.em.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import static org.junit.Assert.assertNotNull;
import org.junit.Rule;
import org.junit.Test;


public class WorkshopTest {

    @Rule
    public EntityManagerProvider emProvider = EntityManagerProvider.persistenceUnit("it");

    @Test
    public void crud() {
        EntityManager em = emProvider.em();
        assertNotNull(em);
        EntityTransaction tx = emProvider.tx();
        assertNotNull(tx);
        tx.begin();
        em.merge(new Workshop("html5", "html5 for javaee developers"));
        tx.commit();
    }
}
```
