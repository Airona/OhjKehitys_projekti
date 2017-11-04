package fi.swd.projektityo.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
	List<Image> findByName(String name);
}