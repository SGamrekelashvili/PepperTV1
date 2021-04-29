package ge.android.gis.tv1.activity.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ge.android.gis.tv1.R
import ge.android.gis.tv1.activity.MainActivity
import ge.android.gis.tv1.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {


    private lateinit var binding: FragmentMainBinding
    private var ma: MainActivity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ma = activity as MainActivity?

        binding = FragmentMainBinding.bind(view)



        binding.buttonPresentation.setOnClickListener {
            FragmentUtils.setFragment(PresentationFragment(), true)
        }


        binding.contactBtn.setOnClickListener {
            ma!!.showContactDialog()
        }



        binding.firstQuestionBtn.setOnClickListener {


            ma!!.tts!!.speakOut("ქროსროუდი შესაძლოა თქვენი წარმატების საწინდარი გახდეს. თქვენი მომავლის შექმნა მხოლოდ თქვენ შეგიძლიათ. ჩვენ კი შეგვიძლია ამაში დაგეხმაროთ!", 13000)


        }

        binding.secondQuestionBtn.setOnClickListener {

            ma!!.tts!!.speakOut("ჩვენი ღონისძიება იქნება საქართველოში, და რეგიონში სტარტაპ ეკოსისტემის მთავარი მამოძრავებელი ძალა. შესაბამისად თუ თქვენ დაინტერესებული ხართ სტარტაპ ეკოსისტემით,, ქროსროუდის პარტნიორობა არის სწორედ ის რაც, თქვენ გჭირდებათ.", 15000)
        }

        binding.thirdQuestionBtn.setOnClickListener {

            ma!!.tts!!.speakOut("2021 წლის სექტემბერში საქართველოს ეწვევა სტარტაპებისა და დამოუკიდებელი მეწარმეების სამყარო!\n" +
                    "ნუთუ,? არ გინდათ, რომ თქვენი ბრენდი, ამ მოვლენასთან ასოცირდებოდეს.??", 14000)

        }

    }



}