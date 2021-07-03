package PaooGame.Graphics;

import javax.sound.sampled.*;
import java.io.IOException;

/*! \class AudioLoader
    \brief Clasa ce contine o metoda statica pentru incarcarea unui sunet din memorie. ( de tip .wav )
 */
public class AudioLoader {
    /*! \fn  public static AudioInputStream loadAudio(String path)
        \brief Incarca un stream audio intr-un obiect AudioInputStream si returneaza o referinta catre acesta.

        \param path Calea relativa pentru localizarea sunetului.
     */
    public static AudioInputStream LoadAudio(String path) {
        /// Avand in vedere exista situatii in care fisierul sursa sa nu poate fi accesat

        /// metoda read() arunca o exceptie ce trebuie tratata
        try {
            return AudioSystem.getAudioInputStream(AudioLoader.class.getResource(path));
        } catch (UnsupportedAudioFileException | IOException e) {
            /// Afiseaza informatiile necesare depanarii.
            e.printStackTrace();
        }
        return null;
    }

    public static void setVolume(Clip song, int volume) {
        FloatControl control = (FloatControl) song.getControl(FloatControl.Type.MASTER_GAIN);
        float range = control.getMinimum();
        float result = range * (1 - volume / 100.0f);
        control.setValue(result);
    }
}
