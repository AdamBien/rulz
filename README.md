# rulz
Reusable JUnit Rules

See Rulz in action: ["Effective Java EE" online workshop, episode 5](http://effectivejavaee.com) and in the ["Java EE Testing and Quality Workshop"](http://javaeetesting.com)

## JPA EntityManager provider

### Installation

```xml
    <dependency>
        <groupId>com.airhacks.rulz</groupId>
        <artifactId>em</artifactId>
        <version>[RECENT_VERSION]</version>
        <scope>test</scope>
    </dependency>
```
### Sample use

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
## JAX-RS 2.0 Client provider

### Installation

```xml
        <dependency>
            <groupId>com.airhacks.rulz</groupId>
            <artifactId>jaxrsclient</artifactId>
            <version>[RECENT_VERSION]</version>
            <scope>test</scope>
        </dependency>
```

### Sample use

```java
import static com.airhacks.rulz.jaxrsclient.HttpMatchers.successful;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;

public class JAXRSClientProviderTest {

    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://www.java.com");

    @Test
    public void pingJava() {
        Client client = provider.client();
        assertNotNull(client);
        WebTarget target = provider.target();
        assertNotNull(target);
        Response response = target.request(MediaType.TEXT_HTML).get();
        assertThat(response.getStatus(), is(200));
    }
    
    @Test
    public void pingJavaAndVerifyWithMatcher() {
        //(...)
        Response response = target.request(MediaType.TEXT_HTML).get();
        assertThat(response, is(successful()));
    }
}
```

JAX-RS client rules are tested with [statustest](https://github.com/AdamBien/statustest)
