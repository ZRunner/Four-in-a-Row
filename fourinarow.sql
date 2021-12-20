-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : jeu. 18 nov. 2021 à 14:08
-- Version du serveur :  8.0.27-0ubuntu0.20.04.1
-- Version de PHP : 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `fourinarow`
--
CREATE DATABASE IF NOT EXISTS `fourinarow` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `fourinarow`;

-- --------------------------------------------------------

--
-- Structure de la table `history`
--
-- Création : jeu. 18 nov. 2021 à 12:42
-- Dernière modification : jeu. 18 nov. 2021 à 09:36
--

CREATE TABLE `history` (
  `log_id` int NOT NULL,
  `user_id` int NOT NULL,
  `game_id` bigint NOT NULL,
  `game_type` varchar(32) NOT NULL,
  `from_ai` tinyint(1) NOT NULL,
  `current_state` text NOT NULL,
  `chosen_move` smallint NOT NULL,
  `won_game` tinyint(1) NOT NULL,
  `moves_before_end` smallint UNSIGNED NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONS POUR LA TABLE `history`:
--

-- --------------------------------------------------------

--
-- Structure de la table `tokens`
--
-- Création : ven. 05 nov. 2021 à 10:33
-- Dernière modification : jeu. 18 nov. 2021 à 10:13
--

CREATE TABLE `tokens` (
  `token` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONS POUR LA TABLE `tokens`:
--

-- --------------------------------------------------------

--
-- Structure de la table `users`
--
-- Création : ven. 05 nov. 2021 à 10:16
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- RELATIONS POUR LA TABLE `users`:
--

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `won_game` (`won_game`),
  ADD KEY `game_id` (`game_id`),
  ADD KEY `game_type` (`game_type`);

--
-- Index pour la table `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`token`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `history`
--
ALTER TABLE `history`
  MODIFY `log_id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
