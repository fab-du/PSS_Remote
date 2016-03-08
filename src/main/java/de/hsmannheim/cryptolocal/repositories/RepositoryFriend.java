package de.hsmannheim.cryptolocal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsmannheim.cryptolocal.models.Friendship;

public interface RepositoryFriend extends JpaRepository<Friendship, Long>{
}
