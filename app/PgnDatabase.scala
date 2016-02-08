package lila.openingexplorer

import java.io.File

import fm.last.commons.kyoto.{KyotoDb, WritableVisitor}
import fm.last.commons.kyoto.factory.{KyotoDbBuilder, Mode, Compressor, PageComparator}

import chess.{Hash, Situation, Move, PositionHash}

final class PgnDatabase extends MasterDatabasePacker {

  private val dbFile = new File("data/master-pgn.kct")
  dbFile.createNewFile

  private val db =
    new KyotoDbBuilder(dbFile)
      .modes(Mode.CREATE, Mode.READ_WRITE)
      .buckets(2000000L)
      .compressor(Compressor.LZMA)
      .pageComparator(PageComparator.LEXICAL)
      .buildAndOpen

  def get(gameId: String): Option[String] = Option(db.get(gameId))

  def store(gameId: String, pgn: String) = db.set(gameId, pgn)

  def close = {
    db.close()
  }
}
