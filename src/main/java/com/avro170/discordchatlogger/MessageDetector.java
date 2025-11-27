package com.avro170.discordchatlogger;

public class MessageDetector {

    private static final String[] DEATH_KEYWORDS = {
            "a brûlé", "a cazut", "a fost ucis", "a été tué", "afogado", "apdega", "ars", "bhàth",
            "bhádh", "ble drept", "blev dræbt", "boddi", "bol zabitý", "boğuldu", "brent", "brände",
            "brændt", "burned", "buvo nužudytas", "buvo sugriauta", "byl zabit", "bị giết", "bị nổ",
            "caiu", "cayó", "cháy", "chết", "chết đuối", "cremar", "cwympodd", "dibunuh", "died",
            "drowned", "druknede", "druknet", "drunknade", "dóite", "dödades", "düşdü", "düştü",
            "eksplodował", "entered the exit portal", "erori zen", "erre zen", "erriaren", "erstickt",
            "ertrank", "esclatar", "esett", "est tombé", "explodat", "exploded", "explodeerde",
            "explosé", "faldt", "falt", "fell", "fell into", "felrobbant", "ffrwydrodd", "fiel",
            "foi morto", "fue asesinado", "föll", "gedood", "hil egin zen", "hit the ground", "hukkuminen",
            "jatuh", "je eksplodirao", "je pao", "je spaljen", "je ubijen", "je utonuo", "kelaparan",
            "kuanguka", "kuchemka", "kukkus", "kuoli", "kuteketezwa", "kuzama", "lehertu", "lladrata",
            "losgadh", "losgwyd", "maraithe", "marbh", "megfulladt", "megégett", "megölt", "meledak",
            "morreu", "mourir", "murió", "nabugo", "namatayan", "nasigasig", "nasunog", "naunom",
            "ngạt thở", "nogalināts", "noslīka", "ontplof", "padl", "padol", "queimado", "räjähtävä",
            "rơi", "s'est noyé", "sa utopil", "se ahogó", "se asfixió", "se quemó", "si è annegato",
            "skendo", "skjuten", "spadł", "sprengt", "sprængdes", "sprængt", "spálené", "spálený",
            "spłonął", "squashed", "starb", "starved", "stierf", "suffocated", "suffoqué", "sufocado",
            "sultet", "sumabog", "svält", "tappoi", "tenggelam", "terbakar", "tercekik", "terjatuh",
            "tersedak", "tewas", "thit", "thuit", "transpercé", "tukehtua", "ubulawa", "udusil",
            "udušil", "uliuawa", "umarł z głodu", "umgekommen", "utonął", "va caure", "va cremar",
            "va ser assassinat", "val", "verbrand", "verbrandde", "verbrannt", "verbrannte", "verdrink",
            "verdronk", "verstik", "viel", "vuruşturuldu", "vybuchli", "vybuchly", "wa cazut", "waboshwa",
            "wadubula", "waluluza", "was bevroren", "was blown up", "was frozen", "was incinerated",
            "was killed", "was killed by", "was poked", "was pricked", "was pummeled", "was shot",
            "was slain", "wawa", "went off with a bang", "werd gedood", "werd geprikt", "werd gestoken",
            "werd platgewalst", "wurde gesprengt", "wurde getötet", "yandı", "zadławił się", "zemřel",
            "zginął", "zomrel", "został przebity", "został zabity", "è caduto", "è esploso", "è morto",
            "è stato bruciato", "è stato ucciso", "öldürüldü", "батып кетті", "був вражений",
            "був заморожений", "був убитий", "был взорван", "был заморожен", "был застрелен",
            "был поражен", "был раздавлен", "был убит", "вибухнув", "впав", "голодал", "жанды",
            "жарылды", "задихнувся", "задохнулся", "згорів", "погиб", "помер", "потонув", "сгорел",
            "убил", "умер", "упал", "утонул", "गिरा", "चुभो दिया", "जला", "डूबा", "दम घुटा",
            "मार दिया", "विस्फोट", "ক্ষুধা", "জ্বলেছে", "ডুবে গেছে", "দম বন্ধ", "পড়ে গেছে",
            "মৃত", "মৃত্যু", "हत्या करा", "ਅੱਗ", "ਜਿਸ", "ਪਾਣੀ", "ਮਾਰਿਆ", "ਮੌਤ", "ਲਾਗ",
            "被杀", "坠落", "溺水", "烧伤", "被炸死", "死亡", "被击杀", "被压死", "被冻", "窒息",
            "被殺", "墜落", "溺水", "燒傷", "被炸死", "死亡", "被擊殺", "被壓死", "被凍", "窒息",
            "殺されました", "落ちました", "溺れました", "焼かれました", "爆破されました", "死亡", "窒息しました",
            "죽었습니다", "떨어졌습니다", "익사했습니다", "불태워졌습니다", "폭발했습니다", "죽음", "질식했습니다"
    };

    private static final String[] JOIN_KEYWORDS = {
            "a radjonde l' djeu", "a rejoint la partie", "a rejoint le jeu", "a rejoint lo joc",
            "a s'é inzòppa into gioco", "a se stà jonto al xogo", "a zo bet ouzhpennet d'an c'hoari",
            "ad ludum venit", "aderiu ao jogo", "al è intirat int la partide", "alijiunga na mchezo",
            "aliĝis al la ludo", "anatengapo mu masewera", "ass Spill bäigedrëtt", "at ides a sa partida",
            "at rejóndu lo jeu", "at vënirvn a partida", "bergabung dengan permainan", "birrasattai",
            "ble med i spillet", "coagyrt gys yn stoyl", "csatlakozott a játékhoz", "darapọ̀ sí ẹkó",
            "dołączył do gry", "entrou no jogo", "forbinder til spillet", "galay ciyaaraha", "gekk til leiksins",
            "gick med i spelet", "ha giocato la partita", "has joined", "hat sei Schpiel beigedu",
            "heeft het spel betreden", "het speletjie aangesluit", "hui ʻia i ka pāʻani",
            "i haʻafaʻa i te tapaʻi", "i mas inap long pleim", "im jalamlok ro eo jerbal",
            "ingħamel għall-logħba", "ioineth the game", "is beim spel gans angeslote", "is bie't spèl gekômen",
            "is to't Speel bidragen", "is zum Spui beigtreten", "ist dem Spiel beigetreten", "jan e sona pi musi",
            "je присоединио igru", "je присоединио се игри", "join'd tha gem", "joined the game",
            "joinis la ludo", "jokora sartu zen", "jungiva al ludo", "jyned the gam", "liittyi peliin",
            "liitus mänguga", "lîstikê de beşdarî bû", "melu ing game", "mvccokate e mvnke",
            "nanoko nampitambatra", "naupay karu", "o shumela mutsimboni", "oansluten by it spul",
            "okaa dee ase", "oyuna katıldı", "oyuna qatıldı", "oʻyinga qoʻshildi", "pievienojās spēlei",
            "prijungėsi prie žaidimo", "przyńdoł do gry", "rinne ar an gcluichí", "s'ha unit al joc",
            "s'ha unitschea cun il tgieu", "s-a alăturat jocului", "sa pripojil k hre", "se ha unido al juego",
            "se je priključil igri", "se připojil ke hře", "se unió al juego", "slóð sær at leikinum",
            "soro ojoro", "sumali sa laro", "telah menyertai permainan", "thàinig anns a' gheama",
            "u bashkua me lojën", "ua sumali i le taaloga", "ujoyinde umdlalo", "uniuse ao xogo",
            "vart med i spelet", "wakazvino muchidzidzo", "wayisulele ku mcwadi", "wiikwegamaawin miinawaa",
            "xunióse al xuegu", "ya shiga wasa", "yinjiye mu musambuke", "ymunodd â'r gêm", "yurwas ɣ urar",
            "đã tham gia trò chơi", "ƃɐɯ ǝɥʇ pǝuᴉoɾ", "συμμετείχε στο παιχνίδι", "ба бозӣ пайвастан шуд",
            "далучыўся да гульні", "долучився до гры", "модасо саевтязь", "ойньӧ вӧлі эскӧдз",
            "ойынға қосылды", "оюнга кошулду", "оҥоһуга кииллэ", "присоединился к игре",
            "приєднався до гри", "се придружи на играта", "се присъедини към играта", "уенга кушылды",
            "уйынға ҡушылды", "је припремљена игру", "միացել է խաղին", "האט זיך צוגעלייגט צום שפּיל",
            "הצטרף למשחק", "ئويۇننىڭ قاتارىنى ئىلتىپ،", "انضم إلى اللعبة", "با بازی وارد شد",
            "گیم میں شمولیت اختیار کی", "खेल में शामिल हुआ", "खेलमा सामिल भए", "खेलमे भाग लेलक",
            "खेलेभ्योः पार्थः", "खेळांत सामिल जाले", "खेळाला सामील झाले", "ਖੇਡ ਵਿੱਚ ਸ਼ਾਮਿਲ ਹੋ ਗਿਆ",
            "ଗେମରେ ଯୋଗ ଦେଲେ", "விளையாட்டில் சேர்ந்தார்", "ఆట ఆడటానికి చేరాడు", "ಆಟದೊಂದಿಗೆ ಸೇರಿಕೊಂಡ",
            "ಕೆಲಸಕ್ಕೆ ಸೊಗಸುಕೊಂಡ", "ក្រឡាទៅលេងហ្គេម", "เข้าร่วมเกม", "ကိမ်းထဲ ဝင္ေရာက္ခဲ့သည္",
            "ဂိမ်းသို့ ပါဝင်ခဲ့သည်", "დაკავშირდა თამაშს", "ወደ ጨዋታ ገብቷል", "ゲームに参加しました",
            "加入了游戏", "加入了遊戲", "加入咗遊戲", "參加了遊戲", "ꯂꯥꯏꯕ ꯑꯃꯥꯌꯇꯝ ꯆꯍꯀꯇꯥ ꯄꯤ", "게임에 참가했습니다"
    };

    private static final String[] LEAVE_KEYWORDS = {
            "a laisseada l' djeu", "a lakaet da an c'hoari", "a lassà ala партida", "a plecat din joc",
            "a quitté la partie", "a quitté le jeu", "a scapà dal xogo", "abandonó el juego",
            "al è partìt de la partide", "amag salida", "at chantadet lo jeu", "atstāja spēli",
            "deixou o xogo", "dh'fhàg an geama", "disconnected", "diwisscorr ow stoyl", "elhagyta a játékot",
            "ferlis fan it spul", "fhág ar an gcluichí", "forladt spillet", "gadawodd y gêm",
            "ha abandonado el juego", "ha deixat la partida", "ha lasciato la partita", "has left",
            "hat das Spiel verlassen", "hatt it geim", "hatt spela lotta", "haʻalele i ka pāʻani",
            "het speletjie verlaat", "hevur læft leikin", "hevur æ farið", "hevur æ farið um tí",
            "hät de spèl ferlooss", "hät s'Spill verlooss", "hät s'Spui vrlåßn", "im jalamlok rwe",
            "je pokинула igru", "je покинула игру", "jokoari irten zen", "ket tua ra ari",
            "lahkus mängust", "left the game", "lîstikê jê bûn", "magterang leiksins", "meninggalkan permainan",
            "mvccokate em mvnke", "nag-alis sa laro", "naiwan sa laro", "ninggal ing game", "niwan nampitandrina",
            "nyumba na mchezo", "opustil hru", "opuszczył grę", "oyundan çıktı", "poistui pelistä",
            "пакідаў гульню", "reliquit ludum", "rời khỏi trò chơi", "saiu do jogo", "salió del xuegu",
            "ude osoro", "umalis sa laro", "verlìss ot spoil", "verlö däs spìl", "wot y tyf",
            "ya bar wasa", "ykwa cem ofi", "ĉesis ludon", "ĝuinis la ludo", "ħallaq mil-logħba",
            "αποχώρησε από το παιχνίδι", "лӧсьӧдчис ойнысь", "напусна играта", "ойныс видчысь",
            "ойындан кеткен", "оюнан кеткен", "покинул игру", "уйындан кеткен", "ушлі з гульні",
            "հեռանցել խաղից", "քար դուրս եկավ", "עזב את המשחק", "بازی را ترک کرد", "غادر اللعبة",
            "खेल छोड़ के गया", "खेल से बाहर निकल गया", "खेळ सोडून गेले", "चला गेला",
            "ಆಟವನ್ನು ತೊರೆದ", "ເລີກຫຼິ້ນເກມ", "မဂိမ်းထဲမှ ထွက်သွားသည်", "დატოვა თამაშმა", "ゲームを離れました",
            "離開了遊戲", "ꯂꯥꯏꯕ ꯑꯃꯥꯌꯇꯝ ꯀꯠ", "게임을 떠났습니다"
    };

    public static boolean isDeath(String message) {
        if (message == null) return false;
        String lower = message.toLowerCase();
        for (String keyword : DEATH_KEYWORDS) {
            if (lower.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isJoin(String message) {
        if (message == null) return false;
        String lower = message.toLowerCase();
        for (String keyword : JOIN_KEYWORDS) {
            if (lower.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLeave(String message) {
        if (message == null) return false;
        String lower = message.toLowerCase();
        for (String keyword : LEAVE_KEYWORDS) {
            if (lower.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
