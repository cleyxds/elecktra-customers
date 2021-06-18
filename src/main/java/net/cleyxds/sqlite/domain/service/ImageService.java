package net.cleyxds.sqlite.domain.service;

import net.cleyxds.sqlite.api.exception.StorageException;
import net.cleyxds.sqlite.api.exception.StorageFileNotFoundException;
import net.cleyxds.sqlite.api.storage.StorageProperties;
import net.cleyxds.sqlite.domain.repository.ImageRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class ImageService implements ImageRepositoryService {

	@Autowired
	private CustomerService customerService;

	private final Path rootLocation;

	@Autowired
	public ImageService(StorageProperties properties) {
		rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			String filename = new Random().nextInt(10) + "-" + file.getOriginalFilename();

			Files.copy(file.getInputStream(), rootLocation.resolve(filename));
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
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
	public Stream<Path> loadAll() {
		try {
			return Files.walk(rootLocation, 1)
					.filter(path -> !path.equals(rootLocation))
					.map(rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}
	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
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
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

}
