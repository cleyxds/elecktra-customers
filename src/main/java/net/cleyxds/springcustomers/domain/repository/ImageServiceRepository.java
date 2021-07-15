package net.cleyxds.springcustomers.domain.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface ImageServiceRepository {

	void store(MultipartFile file, Long id);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	URI loadImageById(Long id);

	boolean deleteById(Long id);

}
