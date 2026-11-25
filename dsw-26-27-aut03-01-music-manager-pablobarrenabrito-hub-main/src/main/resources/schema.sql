CREATE TABLE IF NOT EXISTS `Review` (
                                        `ReviewId` int NOT NULL AUTO_INCREMENT,
                                        `Title` varchar(10) NOT NULL,
    `Description` varchar(30) NOT NULL,
    `Rating` int NOT NULL,
    `ArtistId` int NOT NULL,
    PRIMARY KEY (`ReviewId`),
    KEY `FK_ReviewArtistId` (`artistId`),
    CONSTRAINT `FK_ReviewArtistId` FOREIGN KEY (`ArtistId`) REFERENCES `Artist` (`ArtistId`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `CK_ReviewRating` CHECK ((`Rating` between 1 and 5))
    ) ENGINE=InnoDB;