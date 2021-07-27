package fr.azgin.main.advancedgui;

import fr.azgin.main.MainClass;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import me.blackvein.quests.Quests;
import me.blackvein.quests.Stage;
import me.leoko.advancedgui.manager.ResourceManager;
import me.leoko.advancedgui.utils.*;
import me.leoko.advancedgui.utils.actions.Action;
import me.leoko.advancedgui.utils.components.*;
import me.leoko.advancedgui.utils.components.Component;
import me.leoko.advancedgui.utils.components.TextComponent;
import me.leoko.advancedgui.utils.events.LayoutLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GUIExtension implements LayoutExtension {

    private Layout layout = null;
    MainClass main = MainClass.getInstance();

    private String BTN1 = "QPaSzlG4";
    private String BTN2 = "1iIBiDqt";
    Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");

    @Override
    public void onLayoutLoad(LayoutLoadEvent event) {

        final Layout layout = event.getLayout();
        if(layout.getName().equals("TestPlugin")){
            this.layout = layout;

            setupTest();
        } else if(layout.getName().equals("TestSelect")){
            this.layout = layout;

            setupSelect();
        } else if(layout.getName().equals("player_menu")){
            this.layout = layout;

            setupMenu();
        }
    }


    public void setupMenu(){

        /*
        player kill on quests menu
         */
        this.layout.getComponentTree().locate("mZAzQoAt").setClickAction(this::setQuestList);
    }

    public void setQuestList(Interaction context, Player player, boolean primaryTrigger){

        final Font font = ResourceManager.getInstance().getFont("VT323", 18);

        Layout layout = context.getLayout();

        ViewComponent _view = (ViewComponent) layout.getComponentTree().locate("kgp26xDi");
        _view.setView(context, "0PywIjCl");

        List<String> quest_list = new ArrayList<String>();

        Quester quester = qp.getQuester(player.getUniqueId());
        ConcurrentHashMap<Quest, Integer> quests_list = quester.getCurrentQuests();

        quests_list.forEach((quest, integer) -> {
            quest_list.add(quest.getName());
            main.logger.info(quest.getName());
        });

        ListItemBuilder<String> itemBuilder = (interaction, index, item) -> new TextComponent("", (contextt, playert, primaryTriggert) -> {

            setQuest(item, context);

        }, 0, (60) + (20*index) , font,"Quête: " + item, Color.blue);

        // Setup history-list
        ListComponent<String> test = new ListComponent<String>("", null,
                quest_list, itemBuilder, 2, 1);

        ((DummyComponent) layout.getComponentTree().locate("7wzWX39Z")).setComponent(test);

        layout.getComponentTree().locate("y7iy8XM8").setClickAction(
                (contextt, playert, primaryTriggert) -> test.nextPage(context)
        );

        ((CheckComponent) layout.getComponentTree().locate("ZVBfaOJs")).setCheck(
                (contextt, playert, primaryTriggert) -> test.getPage(context) != test.getMaxPage(context)
        );


        layout.getComponentTree().locate("2FYZ6Zv1").setClickAction(
                (contextt, playert, primaryTriggert) -> test.previousPage(context)
        );

        ((CheckComponent) layout.getComponentTree().locate("7heZC9Jo")).setCheck(
                (contextt, t, primaryTriggert) -> test.getPage(context) != 0
        );


    }

    public void setQuest(String quest_name, Interaction context){
        final Font font = ResourceManager.getInstance().getFont("Oswald", 12);

        Layout _layout = context.getLayout();

        ((CheckComponent) _layout.getComponentTree().locate("SXaiQ40j")).setCheck(
                (contextt, player, primaryTrigger) -> false
        );

        _layout.getComponentTree().locate("FqqXoJY0").setClickAction((context1, player, primaryTrigger) -> ((CheckComponent) _layout.getComponentTree().locate("SXaiQ40j")).setCheck(
                (contexttt, playert, primaryTriggert) -> true
        ));

        Quest _quest = qp.getQuest(quest_name);

        LinkedList<Stage> _states = _quest.getStages();
        List<String> _states_script = new ArrayList<>();

        for(Stage _state: _states){
            _states_script.add(_state.getStartMessage());
        }

        ListItemBuilder<String> stageBuilder = (interaction, index, item) -> new TextComponent("", null, 5, (75) + (20*index) , font, "- " + item, Color.blue);

        ListComponent<String> test = new ListComponent<>("", null,
                _states_script, stageBuilder, 2, 1);

        ((DummyComponent) _layout.getComponentTree().locate("R8i5s7OC")).setComponent(test);

        TextComponent quest_name_gui = (TextComponent) _layout.getComponentTree().locate("mIIYs8aV");
        quest_name_gui.setText(_quest.getName());
    }

    public void setupSelect(){
        this.layout.getComponentTree().locate("FvG5TSSI").setClickAction(new Action() {
            @Override
            public void execute(Interaction context, Player player, boolean primaryTrigger) {
                String _testmeta = "mineslash_gui_test_1";
                List<MetadataValue> dieu = player.getMetadata(_testmeta);
                MetadataValue _val = dieu.get(dieu.size()-1);


                Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                        player.performCommand("buy " + _val);
                        player.removeMetadata(_testmeta, main);
                }, (20)*2);

            }
        });
    }

    public void setupTest(){

        final Font font = ResourceManager.getInstance().getFont("VT323", 18);

        ListItemBuilder<String> itemBuilder = (interaction, index, item) -> new TextComponent("", (context, player, primaryTrigger) -> player.setMetadata("mineslash_gui_test_1", new FixedMetadataValue(main, item)), 0, (20) + (20*index) , font,"Test: " + item, Color.blue);

        ArrayList<String> testings = new ArrayList<String>();
        testings.add("test");
        testings.add("test1");
        testings.add("test2");
        testings.add("test3");
        testings.add("test4");

        // Setup history-list
        ListComponent<String> test = new ListComponent<String>("", null,
                testings, itemBuilder, 2, 1);

        ((DummyComponent) layout.getComponentTree().locate("xUrRPZZ5")).setComponent(test);

        layout.getComponentTree().locate(BTN1).setClickAction(
                (context, player, primaryTrigger) -> test.nextPage(context)
        );

        layout.getComponentTree().locate(BTN2).setClickAction(
                (context, player, primaryTrigger) -> test.previousPage(context)
        );

        ((CheckComponent) layout.getComponentTree().locate("9Dhw1VZJ")).setCheck(
                (context, player, primaryTrigger) -> test.getPage(context) != 0
        );

        ((CheckComponent) layout.getComponentTree().locate("hLnalltj")).setCheck(
                (context, player, primaryTrigger) -> test.getPage(context) != test.getMaxPage(context)
        );

        main.logger.info("test");
    }
}
