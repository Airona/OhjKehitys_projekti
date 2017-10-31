package fi.swd.projektityo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ExampleRepository extends CrudRepository<Example, Long> {
	List<Example> findByItem(String item);
}
