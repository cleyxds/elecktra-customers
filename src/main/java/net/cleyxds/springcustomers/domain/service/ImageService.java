package net.cleyxds.springcustomers.domain.service;

import net.cleyxds.springcustomers.api.controller.ImageUploadController;
import net.cleyxds.springcustomers.api.exception.StorageException;
import net.cleyxds.springcustomers.api.exception.StorageFileNotFoundException;
import net.cleyxds.springcustomers.domain.storage.StorageProperties;
import net.cleyxds.springcustomers.domain.repository.ImageServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class ImageService implements ImageServiceRepository {

	@Autowired
	private CustomerService customerService;

	private final Path rootLocation;

	@Autowired
	public ImageService(StorageProperties properties) {
		rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public void store(MultipartFile file, Long id) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}

			String filename = id + "-" + file.getOriginalFilename().toLowerCase();

			customerService.attachImage(id, filename);

			Files.copy(file.getInputStream(), rootLocation.resolve(filename));
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public URI loadImageById(Long id) {
		try {
			var image = Files.walk(rootLocation, 1)
				.filter(filename -> filename.getFileName().toString().contains(id.toString()))
				.findFirst()
				.get();

			var imageUri = MvcUriComponentsBuilder
				.fromMethodName(
					ImageUploadController.class,
					"serveImageUrl",
					image.getFileName().toString()
				)
				.build()
				.toUri();

			return imageUri;
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public boolean deleteById(Long id) {
		var result = FileSystemUtils.deleteRecursively(loadPathById(id).toFile());
		return result;
	}

	private Path loadPathById(Long id) {
		try {
			var path = Files.walk(rootLocation, 1)
				.filter(filename -> filename.getFileName().toString().contains(id.toString()))
				.findFirst()
				.get();
			return path;
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(rootLocation, 1)
					.filter(path -> !path.equals(rootLocation))
					.map(rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

}
