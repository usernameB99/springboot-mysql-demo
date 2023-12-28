import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface NeedRepository extends CrudRepository<Need, Long> {
    List<Need> findByOwner(User owner);
}