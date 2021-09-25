package com.cleyxds.springcustomers.domain.services.impl;

import com.cleyxds.springcustomers.domain.services.interfaces.ImageServiceRepo;
import com.cleyxds.springcustomers.api.controllers.ImageController;
import com.cleyxds.springcustomers.api.exceptions.StorageException;
import com.cleyxds.springcustomers.api.exceptions.StorageFileNotFoundException;
import com.cleyxds.springcustomers.domain.services.storage.StorageProperties;
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
public class ImageServiceImpl implements ImageServiceRepo {

	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	private final Path rootLocation;

	@Autowired
	public ImageServiceImpl(StorageProperties properties) {
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

			var filename = id + "-" + file.getOriginalFilename().toLowerCase();

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

			return (
				MvcUriComponentsBuilder
					.fromMethodName(
						ImageController.class,
						"serveImageUrl",
						image.getFileName().toString()
					)
					.build()
					.toUri()
			);
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
		return FileSystemUtils.deleteRecursively(loadPathById(id).toFile());
	}

	@Override
	public Path loadPathById(Long id) {
		try {
			return (
				Files.walk(rootLocation, 1)
					.filter(filename -> filename.getFileName().toString().contains(id.toString()))
					.findFirst()
					.orElse(null)
			);
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
