package com.chess.engine.board.variants;

import com.chess.engine.board.*;
import com.chess.engine.board.variants.ActiveBoard.ActiveBoardBuilder;
import com.chess.engine.board.variants.AlmostChess.AlmostChessBuilder;
import com.chess.engine.board.variants.AugmentedKnight.AugmentedKnight1;
import com.chess.engine.board.variants.AugmentedKnight.AugmentedKnightBuilder;
import com.chess.engine.board.variants.BearChess.BearBuilder;
import com.chess.engine.board.variants.Berolina.BerolinaBuilder;
import com.chess.engine.board.variants.CapablancaChess.CapablancaChessBuilder;
import com.chess.engine.board.variants.CapablancaRandomChess.CapablancaRandomBuilder;
import com.chess.engine.board.variants.Casablanca.CasablancaBuilder;
import com.chess.engine.board.variants.ChessWithDifferentArmies.ChessWithDifferentArmiesBuilder;
import com.chess.engine.board.variants.JesterChess.JesterBuilder;
import com.chess.engine.board.variants.Displacement.DisplacementChessBuilder;
import com.chess.engine.board.variants.DoubleChess.DoubleBuilder;
import com.chess.engine.board.variants.DusnanyChess.DusnanyBuilder;
import com.chess.engine.board.variants.FalconHunterBoard.FalconHunterBuilder;
import com.chess.engine.board.variants.FicherRandom.FicherRandomBuilder;
import com.chess.engine.board.variants.GrandChess.GrandBuilder;
import com.chess.engine.board.variants.GrantAcedrex.GrantAcedrexBuilder;
import com.chess.engine.board.variants.GrasshoperChess.GrasshoperBuilder;
import com.chess.engine.board.variants.GrossChess.GrossBuilder;
import com.chess.engine.board.variants.LadoreanChess.LadoreanChessBuilder;
import com.chess.engine.board.variants.LosAlamos.LosAlamosBuilder;
import com.chess.engine.board.variants.MaharajahSepoys.MaharajahSepoysBuilder;
import com.chess.engine.board.variants.Metamachy.MetamachyBuilder;
import com.chess.engine.board.variants.ModernChess.ModernBuilder;
import com.chess.engine.board.variants.MotherOfAllBattles.MotherOfAllBattlesBuilder;
import com.chess.engine.board.variants.PaulovichVariation.PaulovichVariationBuilder;
import com.chess.engine.board.variants.PawnEndgame.PawnEndgameBuilder;
import com.chess.engine.board.variants.PeasantRevolt.PeasantRevoltBuilder;
import com.chess.engine.board.variants.ReallyBadChess.ReallyBadBuilder;
import com.chess.engine.board.variants.SixteenPawns.SixteenPawnsBuilder;
import com.chess.engine.board.variants.SortAlmostChess.SortAlmostBuilder;
import com.chess.engine.board.variants.SpartanChess.SpartanBuilder;
import com.chess.engine.board.variants.StandardBoard.StandardBuilder;
import com.chess.engine.board.variants.StratomicChess.StratomicBuilder;
import com.chess.engine.board.variants.SuperXChess.SuperXBuilder;
import com.chess.engine.board.variants.TorpedoChess.TorpedoBuilder;
import com.chess.engine.board.variants.Transcendental.TranscendentalBuilder;
import com.chess.engine.board.variants.TweedleChess.TweedleChessBuilder;
import com.chess.engine.board.variants.Weak.WeakBuilder;
import com.chess.engine.board.variants.WildebeastChess.WildebeastBuilder;
import com.chess.engine.board.variants.WolfChess.WolfBuilder;
import com.chess.engine.board.variants.UniversChess.UniversChessBuilder; // Add this import

public enum Variants {

    STANDARD_BOARD {
        @Override
        public Board startBoard() {
            return StandardBoard.createBoard();
        }

        @Override
        public String getName() {
            return "Standard Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(StandardBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
    },METAMACHY {
        @Override
        public Board startBoard() {
            return Metamachy.createBoard();
        }

        @Override
        public String getName() {
            return "Metamachy Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(MetamachyBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },MOTHER_OF_ALL_BATTTLES {
        @Override
        public Board startBoard() {
            return MotherOfAllBattles.createBoard();
        }

        @Override
        public String getName() {
            return "Mother of all Battles Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(MotherOfAllBattlesBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },GROSS_CHESS {
        @Override
        public Board startBoard() {
            return GrossChess.createBoard();
        }

        @Override
        public String getName() {
            return "Gross Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(GrossBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },CHESS_WITH_DIFFERENT_ARMIES {
        @Override
        public Board startBoard() {
            return ChessWithDifferentArmies.createBoard();
        }

        @Override
        public String getName() {
            return "Chess with Different Armies Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(ChessWithDifferentArmiesBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },
    JESTER_CHESS {
        @Override
        public Board startBoard() {
            return JesterChess.createBoard();
        }

        @Override
        public String getName() {
            return "Jester Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(JesterBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },GRANT_ACEDREX {
        @Override
        public Board startBoard() {
            return GrantAcedrex.createBoard();
        }

        @Override
        public String getName() {
            return "Grant Acedrex Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(GrantAcedrexBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },STRATOMIC {
        @Override
        public Board startBoard() {
            return StratomicChess.createBoard();
        }

        @Override
        public String getName() {
            return "Stratomic Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(StratomicBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },WILDEBEAST_CHESS {
        @Override
        public Board startBoard() {
            return WildebeastChess.createBoard();
        }

        @Override
        public String getName() {
            return "Wildebeast Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(WildebeastBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },WOLF_CHESS {
        @Override
        public Board startBoard() {
            return WolfChess.createBoard();
        }

        @Override
        public String getName() {
            return "Wolf Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(WolfBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    }, SIXTEEN_PAWN_1 {
        @Override
        public Board startBoard() {
            return SixteenPawns.createBoard1();
        }

        @Override
        public String getName() {
            return "Sixteen Pawn Posisition 1";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(SixteenPawnsBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.DIFFERENT_ARMIES;
		}
    },SIXTEEN_PAWN_2 {
        @Override
        public Board startBoard() {
            return SixteenPawns.createBoard2();
        }

        @Override
        public String getName() {
            return "Sixteen Pawn Posisition 2";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(SixteenPawnsBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.DIFFERENT_ARMIES;
		}
    },SIXTEEN_PAWN_3 {
        @Override
        public Board startBoard() {
            return SixteenPawns.createBoard3();
        }

        @Override
        public String getName() {
            return "Sixteen Pawn Posisition 3";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(SixteenPawnsBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.DIFFERENT_ARMIES;
		}
    },SIXTEEN_PAWN_4 {
        @Override
        public Board startBoard() {
            return SixteenPawns.createBoard4();
        }

        @Override
        public String getName() {
            return "Sixteen Pawn Posisition 4";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(SixteenPawnsBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.DIFFERENT_ARMIES;
		}
    },SPARTAN_CHESS {
        @Override
        public Board startBoard() {
            return SpartanChess.createBoard();
        }

        @Override
        public String getName() {
            return "Spartan Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(SpartanBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },DOUBLE_CHESS {
        @Override
        public Board startBoard() {
            return DoubleChess.createBoard();
        }

        @Override
        public String getName() {
            return "Double Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(DoubleBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
    },TWEEDLE_CHESS {
        @Override
        public Board startBoard() {
            return TweedleChess.createBoard();
        }

        @Override
        public String getName() {
            return "Tweedle Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(TweedleChessBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
    },BEAR_CHESS {
        @Override
        public Board startBoard() {
            return BearChess.createBoard();
        }

        @Override
        public String getName() {
            return "Bear Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(BearBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },AUGMENTED_KNIGHT1 {
        @Override
        public Board startBoard() {
            return AugmentedKnight.createBoard1();
        }

        @Override
        public String getName() {
            return "Augmented Knight 1 Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(AugmentedKnightBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },AUGMENTED_KNIGHT2 {
        @Override
        public Board startBoard() {
            return AugmentedKnight.createBoard2();
        }

        @Override
        public String getName() {
            return "Augmented Knight 2 Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(AugmentedKnightBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },AUGMENTED_KNIGHT3 {
        @Override
        public Board startBoard() {
            return AugmentedKnight.createBoard3();
        }

        @Override
        public String getName() {
            return "Augmented Knight 3 Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(AugmentedKnightBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },AUGMENTED_KNIGHT4 {
        @Override
        public Board startBoard() {
            return AugmentedKnight.createBoard4();
        }

        @Override
        public String getName() {
            return "Augmented Knight 4 Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(AugmentedKnightBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },
    GRAND_CHESS {
        @Override
        public Board startBoard() {
            return GrandChess.createBoard();
        }

        @Override
        public String getName() {
            return "Grand Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(GrandBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.CAPABLANCA;
		}
    },  TORPEDO_CHESS{
        @Override
        public Board startBoard() {
            return TorpedoChess.createBoard();
        }

        @Override
        public String getName() {
            return "Torpedo Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(TorpedoBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },CAPABLANCA_RANDOM{
        @Override
        public Board startBoard() {
            return CapablancaRandomChess.createBoard();
        }

        @Override
        public String getName() {
            return "Capablanca Random Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(CapablancaRandomBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.CAPABLANCA;
		}
    },MAHARAJAH_SEPOYS{
        @Override
        public Board startBoard() {
            return MaharajahSepoys.createBoard();
        }

        @Override
        public String getName() {
            return "Maharajah and Sepoys Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(MaharajahSepoysBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
    },MODERN{
        @Override
        public Board startBoard() {
            return ModernChess.createBoard();
        }

        @Override
        public String getName() {
            return "Modern Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(ModernBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.CAPABLANCA;
		}
    },
    DUSNANY_CHESS {
        @Override
        public Board startBoard() {
            return DusnanyChess.createBoard();
        }

        @Override
        public String getName() {
            return "Dusnany Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(DusnanyBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
    },SORT_OF_ALMOST {
        @Override
        public Board startBoard() {
            return SortAlmostChess.createBoard();
        }

        @Override
        public String getName() {
            return "Sort of Almost Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(SortAlmostBuilder.class);
        }

		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
    },
    PEASANT_REVOLT {
		@Override
		public VariantType varaintType() {
			return VariantType.DIFFERENT_ARMIES;
		}
        @Override
        public Board startBoard() {
            return PeasantRevolt.createBoard();
        }

        @Override
        public String getName() {
            return "Peasant's Revolt";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(PeasantRevoltBuilder.class);
        }
    },
    WEAK {
		@Override
		public VariantType varaintType() {
			return VariantType.DIFFERENT_ARMIES;
		}
        @Override
        public Board startBoard() {
            return Weak.createBoard();
        }

        @Override
        public String getName() {
            return "Weak!";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(WeakBuilder.class);
        }
    },
    REALLY_BAD {
		@Override
		public VariantType varaintType() {
			return VariantType.DIFFERENT_ARMIES;
		}
        @Override
        public Board startBoard() {
            return ReallyBadChess.createBoard();
        }

        @Override
        public String getName() {
            return "Really Bad Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(ReallyBadBuilder.class);
        }
    },
    TRANSCENDENTAL {
		@Override
		public VariantType varaintType() {
			return VariantType.RANDOM;
		}
        @Override
        public Board startBoard() {
            return Transcendental.createBoard();
        }

        @Override
        public String getName() {
            return "Transcendental Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(TranscendentalBuilder.class);
        }
    }, SUPERX {
		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
        @Override
        public Board startBoard() {
            return SuperXChess.createBoard();
        }

        @Override
        public String getName() {
            return "Super X";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(SuperXBuilder.class);
        }
    },
    BEROLINA {
		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
        @Override
        public Board startBoard() {
            return Berolina.createBoard();
        }

        @Override
        public String getName() {
            return "Berolina Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(BerolinaBuilder.class);
        }
    },
    FICHER_RANDOM {
		@Override
		public VariantType varaintType() {
			return VariantType.RANDOM;
		}
        @Override
        public Board startBoard() {
            return FicherRandom.createBoard();
        }

        @Override
        public String getName() {
            return "Fischer Random/Chess960";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(FicherRandomBuilder.class);
        }
    },
    LOS_ALAMOS {
		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
        @Override
        public Board startBoard() {
            return LosAlamos.createBoard();
        }

        @Override
        public String getName() {
            return "Los Alamos";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(LosAlamosBuilder.class);
        }
    },
    DISPLACEMENT_CHESS {
		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
        @Override
        public Board startBoard() {
            return Displacement.createBoard();
        }

        @Override
        public String getName() {
            return "Displacement Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(DisplacementChessBuilder.class);
        }
    },
    CASABLANCA {
		@Override
		public VariantType varaintType() {
			return VariantType.PLAYER_CHOSEN;
		}
        @Override
        public Board startBoard() {
            return Casablanca.createBoard();
        }

        @Override
        public String getName() {
            return "Casablanca Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(CasablancaBuilder.class);
        }
    },
    PAWN_ENDGAME {
		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
        @Override
        public Board startBoard() {
            return PawnEndgame.createBoard();
        }

        @Override
        public String getName() {
            return "Pawn Endgame";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(PawnEndgameBuilder.class);
        }
    },
    ACTIVE_CHESS {
		@Override
		public VariantType varaintType() {
			return VariantType.FIXED_POS;
		}
        @Override
        public Board startBoard() {
            return ActiveBoard.createBoard();
        }

        @Override
        public String getName() {
            return "Active Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(ActiveBoardBuilder.class);
        }
    },
    FALCON_HUNTER {
		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
        @Override
        public Board startBoard() {
            return FalconHunterBoard.createBoard();
        }

        @Override
        public String getName() {
            return "Falcon Hunter Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(FalconHunterBuilder.class);
        }
    },
    ALMOST_CHESS {
		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
        @Override
        public Board startBoard() {
            return AlmostChess.createBoard();
        }

        @Override
        public String getName() {
            return "Almost Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(AlmostChessBuilder.class);
        }
    },
    CAPABLANCA {
		@Override
		public VariantType varaintType() {
			return VariantType.CAPABLANCA;
		}
        @Override
        public Board startBoard() {
            return CapablancaChess.createBoard();
        }

        @Override
        public String getName() {
            return "Capablanca Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(CapablancaChessBuilder.class);
        }
    },
    Univers_CHESS { // Add this new variant
		@Override
		public VariantType varaintType() {
			return VariantType.CAPABLANCA;
		}
        @Override
        public Board startBoard() {
            return UniversChess.createBoard();
        }

        @Override
        public String getName() {
            return "Univers Chess";
        }

        @Override
        public BuildHandler getHandler() {
            return new BuildHandler<>(UniversChessBuilder.class);
        }
    }, PAULOVICH_VARIANTS{
		@Override
		public VariantType varaintType() {
			return VariantType.CAPABLANCA;
		}
		@Override
		public Board startBoard() {
			return PaulovichVariation.createBoard();
		}

		@Override
		public String getName() {
			return "Paulovich Variants";
		}

		@Override
		public BuildHandler getHandler() {
			return new BuildHandler<>(PaulovichVariationBuilder.class);
		}
    	
    }, LADOREAN{
		@Override
		public VariantType varaintType() {
			return VariantType.CAPABLANCA;
		}
		@Override
		public Board startBoard() {
			return LadoreanChess.createBoard();
		}

		@Override
		public String getName() {
			return "Ladorean Chess";
		}

		@Override
		public BuildHandler getHandler() {
			return new BuildHandler<>(LadoreanChessBuilder.class);
		}
    	
    }, GRASSHOPER{
		@Override
		public VariantType varaintType() {
			return VariantType.FAIRY_CHESS;
		}
		@Override
		public Board startBoard() {
			return GrasshoperChess.createBoard();
		}

		@Override
		public String getName() {
			return "Grasshopper Chess";
		}

		@Override
		public BuildHandler getHandler() {
			return new BuildHandler<>(GrasshoperBuilder.class);
		}
    	
    };

    public abstract Board startBoard();
    public abstract String getName();
    public abstract BuildHandler getHandler();
    public abstract VariantType varaintType();
    
    public enum VariantType {
    	FIXED_POS("Fixed Position", 0), 
    	PLAYER_CHOSEN("Player Chosen", 1),
    	RANDOM("Random", 2),
    	DIFFERENT_ARMIES("Different Army", 3),
    	FAIRY_CHESS("Fairy", 4),
    	CAPABLANCA("Capablanca", 5);
		private String pieceName;
		private int index;
		
		VariantType(final String pieceName, final int index){
			this.pieceName = pieceName;
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}
		@Override
		public String toString() {
			return this.pieceName;
		}
    }
}
