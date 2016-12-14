(ns kinopoisk.server.db.protocol)

(defprotocol user-rep-protocol
	(create [this params])
	(read [this username]))